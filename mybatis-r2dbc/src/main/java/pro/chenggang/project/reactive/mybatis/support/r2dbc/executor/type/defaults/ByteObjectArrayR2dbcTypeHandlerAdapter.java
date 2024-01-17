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
package pro.chenggang.project.reactive.mybatis.support.r2dbc.executor.type.defaults;

import io.r2dbc.spi.Readable;
import io.r2dbc.spi.ReadableMetadata;
import io.r2dbc.spi.Statement;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.executor.parameter.ParameterHandlerContext;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.executor.type.R2dbcTypeHandlerAdapter;

import java.nio.ByteBuffer;

/**
 * The type Byte object array r2dbc type handler adapter.
 *
 * @author Gang Cheng
 * @version 1.0.0
 */
public class ByteObjectArrayR2dbcTypeHandlerAdapter implements R2dbcTypeHandlerAdapter<Byte[]> {

    @Override
    public Class<Byte[]> adaptClazz() {
        return Byte[].class;
    }

    @Override
    public void setParameter(Statement statement, ParameterHandlerContext parameterHandlerContext, Byte[] parameter) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(this.toPrimitives(parameter));
        statement.bind(parameterHandlerContext.getIndex(), byteBuffer);
    }

    @Override
    public Byte[] getResult(Readable readable, ReadableMetadata readableMetadata, String columnName) {
        ByteBuffer byteBuffer = readable.get(columnName, ByteBuffer.class);
        if (null == byteBuffer) {
            return null;
        }
        return this.toByteObjects(byteBuffer.array());
    }

    @Override
    public Byte[] getResult(Readable readable, ReadableMetadata readableMetadata, int columnIndex) {
        ByteBuffer byteBuffer = readable.get(columnIndex, ByteBuffer.class);
        if (null == byteBuffer) {
            return null;
        }
        return this.toByteObjects(byteBuffer.array());
    }

    /**
     * Byte[] -> byte[]
     *
     * @param oBytes
     * @return
     */
    private byte[] toPrimitives(Byte[] oBytes) {
        byte[] bytes = new byte[oBytes.length];
        for (int i = 0; i < oBytes.length; i++) {
            bytes[i] = oBytes[i];
        }
        return bytes;
    }

    /**
     * byte[] -> Byte[]
     *
     * @param oBytes
     * @return
     */
    private Byte[] toByteObjects(byte[] oBytes) {
        Byte[] bytes = new Byte[oBytes.length];
        for (int i = 0; i < oBytes.length; i++) {
            bytes[i] = oBytes[i];
        }
        return bytes;
    }
}
