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
package pro.chenggang.project.reactive.mybatis.support.r2dbc.spring.common.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * auto generated
 * 
 * @author AutoGenerated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Subject {
    protected Integer id;

    protected String name;

    protected Integer age;

    protected Integer height;

    protected Integer weight;

    protected Boolean active;

    protected LocalDateTime dt;

    protected Long length;

    public static final String ID = "id";

    public static final String DB_ID = "id";

    public static final String NAME = "name";

    public static final String DB_NAME = "name";

    public static final String AGE = "age";

    public static final String DB_AGE = "age";

    public static final String HEIGHT = "height";

    public static final String DB_HEIGHT = "height";

    public static final String WEIGHT = "weight";

    public static final String DB_WEIGHT = "weight";

    public static final String ACTIVE = "active";

    public static final String DB_ACTIVE = "active";

    public static final String DT = "dt";

    public static final String DB_DT = "dt";

    public static final String LENGTH = "length";

    public static final String DB_LENGTH = "length";
}