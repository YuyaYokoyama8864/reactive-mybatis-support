/*
 *    Copyright 2009-2023 the original author or authors.
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
package pro.chenggang.project.reactive.mybatis.support.common.mapper.dynamic;

import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;
import pro.chenggang.project.reactive.mybatis.support.common.entity.SubjectDataAnEnum;

import java.math.BigDecimal;
import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * auto generated dynamic mapper
 * 
 * @author autoGenerated
 */
public final class SubjectDataDynamicSqlSupport {
    public static final SubjectData subjectData = new SubjectData();

    public static final SqlColumn<Byte> abyte = subjectData.abyte;

    public static final SqlColumn<Short> ashort = subjectData.ashort;

    public static final SqlColumn<String> achar = subjectData.achar;

    public static final SqlColumn<Integer> anint = subjectData.anint;

    public static final SqlColumn<Long> along = subjectData.along;

    public static final SqlColumn<Float> afloat = subjectData.afloat;

    public static final SqlColumn<Double> adouble = subjectData.adouble;

    public static final SqlColumn<Boolean> aboolean = subjectData.aboolean;

    public static final SqlColumn<String> astring = subjectData.astring;

    public static final SqlColumn<SubjectDataAnEnum> anenum = subjectData.anenum;

    public static final SqlColumn<BigDecimal> adecimal = subjectData.adecimal;

    public static final SqlColumn<Timestamp> atimestamp = subjectData.atimestamp;

    public static final SqlColumn<LocalDate> adate = subjectData.adate;

    public static final SqlColumn<LocalDateTime> adatetime = subjectData.adatetime;

    public static final class SubjectData extends AliasableSqlTable<SubjectData> {
        public final SqlColumn<Byte> abyte = column("aByte", JDBCType.TINYINT);

        public final SqlColumn<Short> ashort = column("aShort", JDBCType.SMALLINT);

        public final SqlColumn<String> achar = column("aChar", JDBCType.CHAR);

        public final SqlColumn<Integer> anint = column("anInt", JDBCType.INTEGER);

        public final SqlColumn<Long> along = column("aLong", JDBCType.BIGINT);

        public final SqlColumn<Float> afloat = column("aFloat", JDBCType.REAL);

        public final SqlColumn<Double> adouble = column("aDouble", JDBCType.DOUBLE);

        public final SqlColumn<Boolean> aboolean = column("aBoolean", JDBCType.BIT);

        public final SqlColumn<String> astring = column("aString", JDBCType.VARCHAR);

        public final SqlColumn<SubjectDataAnEnum> anenum = column("anEnum", JDBCType.VARCHAR);

        public final SqlColumn<BigDecimal> adecimal = column("aDecimal", JDBCType.DECIMAL);

        public final SqlColumn<Timestamp> atimestamp = column("aTimestamp", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDate> adate = column("aDate", JDBCType.DATE);

        public final SqlColumn<LocalDateTime> adatetime = column("aDateTime", JDBCType.TIMESTAMP);

        public SubjectData() {
            super("subject_data", SubjectData::new);
        }
    }
}