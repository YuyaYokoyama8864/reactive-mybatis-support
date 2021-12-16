package pro.chenggang.project.reactive.mybatis.support.r2dbc.connection;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.*;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.lang.Nullable;
import org.springframework.r2dbc.connection.ConnectionFactoryUtils;
import org.springframework.util.ReflectionUtils;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.executor.ReactiveExecutorContext;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.support.ProxyInstanceFactory;
import reactor.core.publisher.Mono;

import java.io.Closeable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author evans
 */
public class DefaultTransactionSupportConnectionFactory implements ConnectionFactory, Wrapped<ConnectionFactory>, Closeable {

	private static final Log log = LogFactory.getLog(DefaultTransactionSupportConnectionFactory.class);

	private final ConnectionFactory targetConnectionFactory;

	public DefaultTransactionSupportConnectionFactory(ConnectionFactory targetConnectionFactory) {
		this.targetConnectionFactory = targetConnectionFactory;
	}

	@Override
	public Mono<? extends Connection> create() {
		return this.getOptionalTransactionAwareConnectionProxy(this.targetConnectionFactory);
	}

	@Override
	public ConnectionFactoryMetadata getMetadata() {
		return this.targetConnectionFactory.getMetadata();
	}

	@Override
	public ConnectionFactory unwrap() {
		return this.targetConnectionFactory;
	}

	/**
	 * close connection factory
	 */
	@Override
	public void close(){
		if(this.targetConnectionFactory instanceof ConnectionPool){
			ConnectionPool connectionPool = ((ConnectionPool) this.targetConnectionFactory);
			if (!connectionPool.isDisposed()) {
				connectionPool.dispose();
			}
		}
	}

	/**
	 * get optional transaction aware connection based on ReactiveExecutorContext's isUsingTransaction()
	 * @param targetConnectionFactory
	 * @return
	 */
	private Mono<Connection> getOptionalTransactionAwareConnectionProxy(ConnectionFactory targetConnectionFactory) {
		return Mono.deferContextual(contextView -> Mono
				.justOrEmpty(contextView.getOrEmpty(ReactiveExecutorContext.class))
				.cast(ReactiveExecutorContext.class)
				.flatMap(reactiveExecutorContext -> Mono.just(reactiveExecutorContext.isUsingTransaction())
						.filter(usingTransaction -> usingTransaction)
						.flatMap(usingTransaction -> {
							log.debug("[Get connection](With transaction)");
							return Mono.justOrEmpty(reactiveExecutorContext.getConnection())
										.switchIfEmpty(Mono.from(targetConnectionFactory.create())
												.map(newConnection -> {
													log.debug("[Get connection](With transaction)Old connection not exist ,Create connection : " + newConnection);
													return this.getConnectionProxy(newConnection, true);
												})
										)
										.doOnNext(transactionConnection -> {
											log.debug("[Get connection](With transaction)Register to context : " + transactionConnection);
											reactiveExecutorContext.registerConnection(transactionConnection);
										})
										//if using transaction then force set auto commit to false
										.flatMap(newConnection -> Mono.justOrEmpty(reactiveExecutorContext.getIsolationLevel())
												.flatMap(isolationLevel -> {
													log.debug("[Get connection](With transaction)Transaction isolation level exist : " + isolationLevel);
													return Mono.from(newConnection.setTransactionIsolationLevel(isolationLevel))
															.then(Mono.defer(() -> {
																log.debug("[Get connection](With transaction)Force set autocommit to false");
																return Mono.from(newConnection.setAutoCommit(false));
															}));
												})
												.switchIfEmpty(Mono.from(newConnection.setAutoCommit(false)))
												.then(Mono.just(newConnection)));
								}
						)
						.switchIfEmpty(Mono.defer(
								() -> {
									log.debug("[Get connection](Simple)");
									return Mono.justOrEmpty(reactiveExecutorContext.getConnection())
											.switchIfEmpty(Mono.from(targetConnectionFactory.create())
													.map(newConnection -> {
														log.debug("[Get connection](Simple)Create connection : " + newConnection);
														return this.getConnectionProxy(newConnection, false);
													})
											)
											.doOnNext(simpleConnection -> {
												log.debug("[Get connection](Simple)Register to context : " + simpleConnection);
												reactiveExecutorContext.registerConnection(simpleConnection);
											});
						})
						//if not using transaction then set auto commit to original auto commit
						.flatMap(newConnection -> {
							log.debug("[Get connection](Simple)Set autoCommit by config : " + reactiveExecutorContext.isAutoCommit());
							return Mono.from(newConnection.setAutoCommit(reactiveExecutorContext.isAutoCommit()))
									.then(Mono.just(newConnection));
						}))
				));
	}

	private Connection getConnectionProxy(Connection connection,boolean suspendClose){
		return ProxyInstanceFactory.newInstanceOfInterfaces(
				Connection.class,
				() -> new TransactionAwareConnection(connection,suspendClose),
				Wrapped.class
		);
	}


	/**
	 * Invocation handler that delegates close calls on R2DBC Connections to
	 * {@link ConnectionFactoryUtils} for being aware of context-bound transactions.
	 */
	private static class TransactionAwareConnection implements InvocationHandler {

		private final Connection connection;

		private boolean closed = false;

		private final boolean suspendClose;

		TransactionAwareConnection(Connection connection, boolean suspendClose) {
			this.connection = connection;
			this.suspendClose = suspendClose;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (ReflectionUtils.isToStringMethod(method)) {
				return proxyToString(proxy);
			}
			if (ReflectionUtils.isEqualsMethod(method)) {
				return (proxy == args[0]);
			}
			if (ReflectionUtils.isHashCodeMethod(method)) {
				return System.identityHashCode(proxy);
			}
			switch (method.getName()) {
				case "toString":
					return proxyToString(proxy);
				case "equals":
					return (proxy == args[0]);
				case "hashCode":
					return System.identityHashCode(proxy);
				case "unwrap":
					return this.connection;
				case "close":
					return Mono.deferContextual(contextView -> Mono
							.justOrEmpty(contextView.getOrEmpty(ReactiveExecutorContext.class))
							.cast(ReactiveExecutorContext.class)
							.flatMap(reactiveExecutorContext -> {
								if(!this.suspendClose){
									log.debug("[Close connection](Simple)Not suspend close,simple close connection");
									return this.executeCloseConnection(reactiveExecutorContext);
								}
								if(reactiveExecutorContext.isForceRollback()){
									return Mono.just(reactiveExecutorContext.isRequireClosed())
											.filter(requireClose -> requireClose)
											.flatMap(requireClose -> {
												log.debug("[Close connection](With Transaction)rollback and close connection");
												return Mono.from(this.connection.rollbackTransaction())
														.then(Mono.defer(
																() -> {
																    reactiveExecutorContext.setForceRollback(false);
                                                                    return this.executeCloseConnection(reactiveExecutorContext);
                                                                }
														));
											})
											.switchIfEmpty(Mono.defer(
													() -> {
														log.debug("[Close connection](With Transaction)just rollback,not close connection");
														reactiveExecutorContext.setForceRollback(false);
														return Mono.from(this.connection.rollbackTransaction())
																.onErrorResume(Exception.class, this::onErrorOperation);
													}
											));
								}
								if(reactiveExecutorContext.isForceCommit()){
									return Mono.just(reactiveExecutorContext.isRequireClosed())
											.filter(requireClose -> requireClose)
											.flatMap(requireClose -> {
												log.debug("[Close connection](With Transaction)commit and close connection");
												return Mono.from(this.connection.commitTransaction())
														.then(Mono.defer(
																() -> {
																    reactiveExecutorContext.setForceCommit(false);
                                                                    return this.executeCloseConnection(reactiveExecutorContext);
                                                                }
														));
											})
											.switchIfEmpty(Mono.defer(
													() -> {
														log.debug("[Close connection](With Transaction)just commit,not close connection");
														reactiveExecutorContext.setForceRollback(false);
														return Mono.from(this.connection.commitTransaction())
																.onErrorResume(Exception.class, this::onErrorOperation);
													}
											));
								}
								if(reactiveExecutorContext.isRequireClosed()){
									log.debug("[Close connection](With Transaction)close connection");
									return this.executeCloseConnection(reactiveExecutorContext);
								}
								log.debug("[Close connection](With Transaction)neither rollback or commit,nothing to do");
								return Mono.empty();
							}));
				case "isClosed":
					return this.closed;
			}

			if (this.closed) {
				throw new IllegalStateException("Connection handle already closed");
			}

			// Invoke method on target Connection.
			try {
				return method.invoke(this.connection, args);
			}
			catch (InvocationTargetException ex) {
				throw ex.getTargetException();
			}
		}

		/**
		 * execute close connection
		 * @param reactiveExecutorContext
		 * @return
		 */
		private Mono<Void> executeCloseConnection(ReactiveExecutorContext reactiveExecutorContext){
			log.debug("[Close Connection]Connection : " + this.connection);
			return Mono.from(this.connection.close())
					.then(Mono.defer(
							() -> Mono.justOrEmpty(reactiveExecutorContext.clearConnection())
									.flatMap(oldConnection -> {
										log.debug("[Close Connection]Clear connection in context : " + oldConnection);
										this.closed = true;
										return Mono.empty();
									})
					))
					.then()
					.onErrorResume(Exception.class, this::onErrorOperation);

		}

		/**
		 * on error operation
		 * @param e
		 * @return
		 */
		private Mono<Void> onErrorOperation(Exception e){
			return Mono.deferContextual(contextView -> Mono
					.justOrEmpty(contextView.getOrEmpty(ReactiveExecutorContext.class))
					.cast(ReactiveExecutorContext.class)
					.flatMap(reactiveExecutorContext -> {
						if(reactiveExecutorContext.isUsingTransaction()){
							return Mono.from(this.connection.rollbackTransaction())
									.then(Mono.defer(() -> Mono.from(this.connection.close())))
									.doOnSubscribe(v -> this.closed = true);
						}
						return Mono.from(this.connection.close())
								.doOnSubscribe(v -> this.closed = true);
					}))
                    .then(Mono.error(e));
		}

		private String proxyToString(@Nullable Object proxy) {
			return "Transaction-support proxy for target Connection [" + this.connection.toString() + "],Original Proxy ["+proxy+"]";
		}

	}
}
