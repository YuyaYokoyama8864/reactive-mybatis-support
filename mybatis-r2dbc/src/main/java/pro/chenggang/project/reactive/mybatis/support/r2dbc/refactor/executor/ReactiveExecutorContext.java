package pro.chenggang.project.reactive.mybatis.support.r2dbc.refactor.executor;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.IsolationLevel;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: chenggang
 * @date 12/9/21.
 */
public class ReactiveExecutorContext {

    private AtomicBoolean usingTransaction;
    private final boolean autoCommit;
    private final IsolationLevel isolationLevel;
    private final AtomicReference<Connection> connectionReference = new AtomicReference<>();
    private final AtomicBoolean forceCommit = new AtomicBoolean(false);
    private final AtomicBoolean forceRollback = new AtomicBoolean(false);
    private final AtomicBoolean requireClosed = new AtomicBoolean(false);
    private final StatementLogHelper statementLogHelper;

    public ReactiveExecutorContext(boolean autoCommit,
                                   IsolationLevel isolationLevel,
                                   StatementLogHelper statementLogHelper) {
        this.autoCommit = autoCommit;
        this.isolationLevel = isolationLevel;
        this.statementLogHelper = statementLogHelper;
    }

    public boolean isUsingTransaction() {
        return usingTransaction.get();
    }

    public void setUsingTransaction(AtomicBoolean usingTransaction){
        this.usingTransaction = usingTransaction;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public boolean isForceCommit() {
        return forceCommit.get();
    }

    public boolean isForceRollback() {
        return forceRollback.get();
    }

    public void setForceCommit(boolean forceCommit){
        this.forceCommit.getAndSet(forceCommit);
    }

    public void setForceRollback(boolean forceRollback){
        this.forceRollback.getAndSet(forceRollback);
    }

    public boolean isRequireClosed(){
        return this.requireClosed.get();
    }

    public void setRequireClosed(boolean requireClosed){
        this.requireClosed.getAndSet(requireClosed);
    }

    public IsolationLevel getIsolationLevel() {
        return isolationLevel;
    }

    public StatementLogHelper getStatementLogHelper() {
        return statementLogHelper;
    }

    public boolean registerConnection(Connection connection){
        return this.connectionReference.compareAndSet(null,connection);
    }

    public Optional<Connection> clearConnection(){
        return Optional.ofNullable(this.connectionReference.getAndSet(null));
    }

    public Optional<Connection> getConnection() {
        return Optional.ofNullable(this.connectionReference.get());
    }

}
