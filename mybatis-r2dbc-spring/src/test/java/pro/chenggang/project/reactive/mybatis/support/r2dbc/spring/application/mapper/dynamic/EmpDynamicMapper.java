package pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.application.mapper.dynamic;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.where.WhereApplier;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.dynamic.CommonSelectMapper;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.dynamic.ReactiveMyBatis3Utils;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.application.entity.model.Emp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.application.mapper.dynamic.EmpDynamicSqlSupport.*;

/**
 * auto generated
 * @author AutoGenerated
 */
@Mapper
public interface EmpDynamicMapper extends CommonSelectMapper {
    BasicColumn[] selectList = BasicColumn.columnList(empNo, empName, job, manager, hireDate, salary, kpi, deptNo, createTime);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    Mono<Long> count(SelectStatementProvider selectStatement);

    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    Mono<Integer> delete(DeleteStatementProvider deleteStatement);

    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @Options(useGeneratedKeys = true,keyProperty = "record.empNo")
    Mono<Integer> insert(InsertStatementProvider<Emp> insertStatement);

    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    Mono<Integer> insertMultiple(MultiRowInsertStatementProvider<Emp> multipleInsertStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("EmpResult")
    Mono<Emp> selectOne(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="EmpResult", value = {
        @Result(column="emp_no", property="empNo", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="emp_name", property="empName", jdbcType=JdbcType.VARCHAR),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="manager", property="manager", jdbcType=JdbcType.VARCHAR),
        @Result(column="hire_date", property="hireDate", jdbcType=JdbcType.DATE),
        @Result(column="salary", property="salary", jdbcType=JdbcType.INTEGER),
        @Result(column="kpi", property="kpi", jdbcType=JdbcType.DECIMAL),
        @Result(column="dept_no", property="deptNo", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Flux<Emp> selectMany(SelectStatementProvider selectStatement);

    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    Mono<Integer> update(UpdateStatementProvider updateStatement);

    default Mono<Long> count(CountDSLCompleter completer) {
        return ReactiveMyBatis3Utils.countFrom(this::count, emp, completer);
    }

    default Mono<Integer> delete(DeleteDSLCompleter completer) {
        return ReactiveMyBatis3Utils.deleteFrom(this::delete, emp, completer);
    }

    default Mono<Integer> insert(Emp record) {
        return ReactiveMyBatis3Utils.insert(this::insert, record, emp, c ->
            c.map(empNo).toProperty("empNo")
            .map(empName).toProperty("empName")
            .map(job).toProperty("job")
            .map(manager).toProperty("manager")
            .map(hireDate).toProperty("hireDate")
            .map(salary).toProperty("salary")
            .map(kpi).toProperty("kpi")
            .map(deptNo).toProperty("deptNo")
            .map(createTime).toProperty("createTime")
        );
    }

    default Mono<Integer> insertMultiple(Collection<Emp> records) {
        return ReactiveMyBatis3Utils.insertMultiple(this::insertMultiple, records, emp, c ->
            c.map(empNo).toProperty("empNo")
            .map(empName).toProperty("empName")
            .map(job).toProperty("job")
            .map(manager).toProperty("manager")
            .map(hireDate).toProperty("hireDate")
            .map(salary).toProperty("salary")
            .map(kpi).toProperty("kpi")
            .map(deptNo).toProperty("deptNo")
            .map(createTime).toProperty("createTime")
        );
    }

    default Mono<Integer> insertSelective(Emp record) {
        return ReactiveMyBatis3Utils.insert(this::insert, record, emp, c ->
            c.map(empNo).toPropertyWhenPresent("empNo", record::getEmpNo)
            .map(empName).toPropertyWhenPresent("empName", record::getEmpName)
            .map(job).toPropertyWhenPresent("job", record::getJob)
            .map(manager).toPropertyWhenPresent("manager", record::getManager)
            .map(hireDate).toPropertyWhenPresent("hireDate", record::getHireDate)
            .map(salary).toPropertyWhenPresent("salary", record::getSalary)
            .map(kpi).toPropertyWhenPresent("kpi", record::getKpi)
            .map(deptNo).toPropertyWhenPresent("deptNo", record::getDeptNo)
            .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
        );
    }

    default Mono<Emp> selectOne(SelectDSLCompleter completer) {
        return ReactiveMyBatis3Utils.selectOne(this::selectOne, selectList, emp, completer);
    }

    default Flux<Emp> select(SelectDSLCompleter completer) {
        return ReactiveMyBatis3Utils.selectList(this::selectMany, selectList, emp, completer);
    }

    default Flux<Emp> selectDistinct(SelectDSLCompleter completer) {
        return ReactiveMyBatis3Utils.selectDistinct(this::selectMany, selectList, emp, completer);
    }

    default Mono<Integer> update(UpdateDSLCompleter completer) {
        return ReactiveMyBatis3Utils.update(this::update, emp, completer);
    }

    default Mono<Integer> updateSelectiveByPrimaryKey(Emp record) {
        return update(c ->
            c.set(empName).equalToWhenPresent(record::getEmpName)
            .set(job).equalToWhenPresent(record::getJob)
            .set(manager).equalToWhenPresent(record::getManager)
            .set(hireDate).equalToWhenPresent(record::getHireDate)
            .set(salary).equalToWhenPresent(record::getSalary)
            .set(kpi).equalToWhenPresent(record::getKpi)
            .set(deptNo).equalToWhenPresent(record::getDeptNo)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .where(empNo, isEqualTo(record::getEmpNo))
        );
    }

    default Mono<Integer> updateAllByPrimaryKey(Emp record) {
        return update(c ->
            c.set(empName).equalToWhenPresent(record::getEmpName)
            .set(job).equalToWhenPresent(record::getJob)
            .set(manager).equalTo(record::getManager)
            .set(hireDate).equalToWhenPresent(record::getHireDate)
            .set(salary).equalToWhenPresent(record::getSalary)
            .set(kpi).equalToWhenPresent(record::getKpi)
            .set(deptNo).equalToWhenPresent(record::getDeptNo)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .where(empNo, isEqualTo(record::getEmpNo))
        );
    }

    default Mono<Integer> updateAll(Emp record, WhereApplier whereApplier) {
        return update(c ->
            c.set(empName).equalToWhenPresent(record::getEmpName)
            .set(job).equalToWhenPresent(record::getJob)
            .set(manager).equalTo(record::getManager)
            .set(hireDate).equalToWhenPresent(record::getHireDate)
            .set(salary).equalToWhenPresent(record::getSalary)
            .set(kpi).equalToWhenPresent(record::getKpi)
            .set(deptNo).equalToWhenPresent(record::getDeptNo)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .applyWhere(whereApplier)
        );
    }

    default Mono<Integer> updateSelective(Emp record, WhereApplier whereApplier) {
        return update(c ->
            c.set(empName).equalToWhenPresent(record::getEmpName)
            .set(job).equalToWhenPresent(record::getJob)
            .set(manager).equalToWhenPresent(record::getManager)
            .set(hireDate).equalToWhenPresent(record::getHireDate)
            .set(salary).equalToWhenPresent(record::getSalary)
            .set(kpi).equalToWhenPresent(record::getKpi)
            .set(deptNo).equalToWhenPresent(record::getDeptNo)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .applyWhere(whereApplier)
        );
    }
}