package pro.chenggang.project.reactive.mybatis.support.r2dbc;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: chenggang
 * @date 12/16/21.
 */
public interface ReactiveSqlSessionOperator {

    /**
     * execute with Mono then commit
     * @param monoExecution
     * @param <T>
     * @return
     */
    <T> Mono<T> executeAndCommit(Mono<T> monoExecution);

    /**
     * execute with Mono then rollback
     * @param monoExecution
     * @param <T>
     * @return
     */
    <T> Mono<T> executeAndRollback(Mono<T> monoExecution);

    /**
     * execute with Flux then commit
     * @param fluxExecution
     * @param <T>
     * @return
     */
    <T> Flux<T> executeManyAndCommit(Flux<T> fluxExecution);

    /**
     * execute with Flux then rollback
     * @param fluxExecution
     * @param <T>
     * @return
     */
    <T> Flux<T> executeManyAndRollback(Flux<T> fluxExecution);

}
