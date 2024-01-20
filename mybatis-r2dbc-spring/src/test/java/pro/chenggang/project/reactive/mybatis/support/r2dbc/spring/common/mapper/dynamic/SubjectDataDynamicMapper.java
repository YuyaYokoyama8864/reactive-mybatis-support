/*
 *    Copyright 2009-2024 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.dynamic.CommonCountMapper;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.dynamic.CommonDeleteMapper;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.dynamic.CommonInsertMapper;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.dynamic.CommonSelectMapper;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.dynamic.CommonUpdateMapper;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.dynamic.ReactiveMyBatis3Utils;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.entity.SubjectData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.aboolean;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.abyte;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.achar;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.adate;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.adatetime;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.adecimal;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.adouble;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.afloat;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.along;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.anenum;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.anint;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.ashort;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.astring;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.atimestamp;
import static pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.mapper.dynamic.SubjectDataDynamicSqlSupport.subjectData;

/**
 * auto generated mapper
 * 
 * @author AutoGenerated
 */
@Mapper
public interface SubjectDataDynamicMapper extends CommonSelectMapper, CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<SubjectData>, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(abyte, ashort, achar, anint, along, afloat, adouble, aboolean, astring, anenum, adecimal, atimestamp, adate, adatetime);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="SubjectDataResult", value = {
        @Result(column="aByte", property="abyte", jdbcType=JdbcType.TINYINT),
        @Result(column="aShort", property="ashort", jdbcType=JdbcType.SMALLINT),
        @Result(column="aChar", property="achar", jdbcType=JdbcType.CHAR),
        @Result(column="anInt", property="anint", jdbcType=JdbcType.INTEGER),
        @Result(column="aLong", property="along", jdbcType=JdbcType.BIGINT),
        @Result(column="aFloat", property="afloat", jdbcType=JdbcType.REAL),
        @Result(column="aDouble", property="adouble", jdbcType=JdbcType.DOUBLE),
        @Result(column="aBoolean", property="aboolean", jdbcType=JdbcType.BIT),
        @Result(column="aString", property="astring", jdbcType=JdbcType.VARCHAR),
        @Result(column="anEnum", property="anenum", jdbcType=JdbcType.VARCHAR),
        @Result(column="aDecimal", property="adecimal", jdbcType=JdbcType.DECIMAL),
        @Result(column="aTimestamp", property="atimestamp", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="aDate", property="adate", jdbcType=JdbcType.DATE),
        @Result(column="aDateTime", property="adatetime", jdbcType=JdbcType.TIMESTAMP)
    })
    Flux<SubjectData> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("SubjectDataResult")
    Mono<SubjectData> selectOne(SelectStatementProvider selectStatement);

    default Mono<Long> count(CountDSLCompleter completer) {
        return ReactiveMyBatis3Utils.countFrom(this::count, subjectData, completer);
    }

    default Mono<Integer> delete(DeleteDSLCompleter completer) {
        return ReactiveMyBatis3Utils.deleteFrom(this::delete, subjectData, completer);
    }

    default Mono<Integer> insert(SubjectData row) {
        return ReactiveMyBatis3Utils.insert(this::insert, row, subjectData, c ->
            c.map(abyte).toProperty("abyte")
            .map(ashort).toProperty("ashort")
            .map(achar).toProperty("achar")
            .map(anint).toProperty("anint")
            .map(along).toProperty("along")
            .map(afloat).toProperty("afloat")
            .map(adouble).toProperty("adouble")
            .map(aboolean).toProperty("aboolean")
            .map(astring).toProperty("astring")
            .map(anenum).toProperty("anenum")
            .map(adecimal).toProperty("adecimal")
            .map(atimestamp).toProperty("atimestamp")
            .map(adate).toProperty("adate")
            .map(adatetime).toProperty("adatetime")
        );
    }

    default Mono<Integer> insertMultiple(Collection<SubjectData> records) {
        return ReactiveMyBatis3Utils.insertMultiple(this::insertMultiple, records, subjectData, c ->
            c.map(abyte).toProperty("abyte")
            .map(ashort).toProperty("ashort")
            .map(achar).toProperty("achar")
            .map(anint).toProperty("anint")
            .map(along).toProperty("along")
            .map(afloat).toProperty("afloat")
            .map(adouble).toProperty("adouble")
            .map(aboolean).toProperty("aboolean")
            .map(astring).toProperty("astring")
            .map(anenum).toProperty("anenum")
            .map(adecimal).toProperty("adecimal")
            .map(atimestamp).toProperty("atimestamp")
            .map(adate).toProperty("adate")
            .map(adatetime).toProperty("adatetime")
        );
    }

    default Mono<Integer> insertSelective(SubjectData row) {
        return ReactiveMyBatis3Utils.insert(this::insert, row, subjectData, c ->
            c.map(abyte).toPropertyWhenPresent("abyte", row::getAbyte)
            .map(ashort).toPropertyWhenPresent("ashort", row::getAshort)
            .map(achar).toPropertyWhenPresent("achar", row::getAchar)
            .map(anint).toPropertyWhenPresent("anint", row::getAnint)
            .map(along).toPropertyWhenPresent("along", row::getAlong)
            .map(afloat).toPropertyWhenPresent("afloat", row::getAfloat)
            .map(adouble).toPropertyWhenPresent("adouble", row::getAdouble)
            .map(aboolean).toPropertyWhenPresent("aboolean", row::getAboolean)
            .map(astring).toPropertyWhenPresent("astring", row::getAstring)
            .map(anenum).toPropertyWhenPresent("anenum", row::getAnenum)
            .map(adecimal).toPropertyWhenPresent("adecimal", row::getAdecimal)
            .map(atimestamp).toPropertyWhenPresent("atimestamp", row::getAtimestamp)
            .map(adate).toPropertyWhenPresent("adate", row::getAdate)
            .map(adatetime).toPropertyWhenPresent("adatetime", row::getAdatetime)
        );
    }

    default Mono<SubjectData> selectOne(SelectDSLCompleter completer) {
        return ReactiveMyBatis3Utils.selectOne(this::selectOne, selectList, subjectData, completer);
    }

    default Flux<SubjectData> select(SelectDSLCompleter completer) {
        return ReactiveMyBatis3Utils.selectList(this::selectMany, selectList, subjectData, completer);
    }

    default Flux<SubjectData> selectDistinct(SelectDSLCompleter completer) {
        return ReactiveMyBatis3Utils.selectDistinct(this::selectMany, selectList, subjectData, completer);
    }

    default Mono<Integer> update(UpdateDSLCompleter completer) {
        return ReactiveMyBatis3Utils.update(this::update, subjectData, completer);
    }

    default Mono<Integer> updateSelectiveByPrimaryKey(SubjectData row) {
        return update(c ->
            c.set(abyte).equalToWhenPresent(row::getAbyte)
            .set(ashort).equalToWhenPresent(row::getAshort)
            .set(achar).equalToWhenPresent(row::getAchar)
            .set(anint).equalToWhenPresent(row::getAnint)
            .set(along).equalToWhenPresent(row::getAlong)
            .set(afloat).equalToWhenPresent(row::getAfloat)
            .set(adouble).equalToWhenPresent(row::getAdouble)
            .set(aboolean).equalToWhenPresent(row::getAboolean)
            .set(astring).equalToWhenPresent(row::getAstring)
            .set(anenum).equalToWhenPresent(row::getAnenum)
            .set(adecimal).equalToWhenPresent(row::getAdecimal)
            .set(atimestamp).equalToWhenPresent(row::getAtimestamp)
            .set(adate).equalToWhenPresent(row::getAdate)
            .set(adatetime).equalToWhenPresent(row::getAdatetime)
        );
    }

    default Mono<Integer> updateAllByPrimaryKey(SubjectData row) {
        return update(c ->
            c.set(abyte).equalTo(row::getAbyte)
            .set(ashort).equalTo(row::getAshort)
            .set(achar).equalTo(row::getAchar)
            .set(anint).equalTo(row::getAnint)
            .set(along).equalTo(row::getAlong)
            .set(afloat).equalTo(row::getAfloat)
            .set(adouble).equalTo(row::getAdouble)
            .set(aboolean).equalTo(row::getAboolean)
            .set(astring).equalTo(row::getAstring)
            .set(anenum).equalTo(row::getAnenum)
            .set(adecimal).equalTo(row::getAdecimal)
            .set(atimestamp).equalToWhenPresent(row::getAtimestamp)
            .set(adate).equalTo(row::getAdate)
            .set(adatetime).equalTo(row::getAdatetime)
        );
    }
}