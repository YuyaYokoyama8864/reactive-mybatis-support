package pro.chenggang.project.reactive.mybatis.support.r2dbc.application.mapper;

import org.apache.ibatis.annotations.Mapper;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.application.entity.extend.EmpWithDept;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.application.entity.model.Emp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * auto generated
 * @author AutoGenerated
 */
@Mapper
public interface EmpMapper {

    Flux<EmpWithDept> selectEmpWithDeptList();

    Flux<Emp> selectByParameterMap(Emp emp);

    Mono<Integer> deleteByDeptNo(Long deptNo);
}