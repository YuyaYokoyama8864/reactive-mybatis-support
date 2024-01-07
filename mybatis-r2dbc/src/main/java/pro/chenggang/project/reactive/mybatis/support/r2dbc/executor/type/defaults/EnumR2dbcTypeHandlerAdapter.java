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
package pro.chenggang.project.reactive.mybatis.support.r2dbc.executor.type.defaults;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.r2dbc.spi.Statement;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.executor.parameter.ParameterHandlerContext;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.executor.type.R2dbcTypeHandlerAdapter;

/**
 * The enum r2dbc type handler adapter
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 2.0.0
 */
public class EnumR2dbcTypeHandlerAdapter<E extends Enum<E>> implements R2dbcTypeHandlerAdapter<E> {

    private final Class<E> type;

    public EnumR2dbcTypeHandlerAdapter(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public Class<E> adaptClazz() {
        return this.type;
    }

    @Override
    public void setParameter(Statement statement, ParameterHandlerContext parameterHandlerContext, E parameter) {
        statement.bind(parameterHandlerContext.getIndex(), parameter.name());
    }

    @Override
    public E getResult(Row row, RowMetadata rowMetadata, String columnName) {
        String result = row.get(columnName, String.class);
        return result == null ? null : Enum.valueOf(type, result);
    }

    @Override
    public E getResult(Row row, RowMetadata rowMetadata, int columnIndex) {
        String result = row.get(columnIndex, String.class);
        return result == null ? null : Enum.valueOf(type, result);
    }
}
