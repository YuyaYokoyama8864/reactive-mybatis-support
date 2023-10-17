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
package pro.chenggang.project.reactive.mybatis.support.test.testcontainers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.containers.MySQLContainer.MYSQL_PORT;

/**
 * The mysql test container initialization
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public class MysqlTestContainerInitialization implements DatabaseInitialization {

    private GenericContainer<?> mysqlTestContainer;

    @Override
    public boolean supportTestContainer(Class<? extends GenericContainer<?>> testContainerClass) {
        return MySQLContainer.class.equals(testContainerClass);
    }

    @Override
    public R2dbcProtocol startup(DatabaseConfig databaseConfig) {
        JdbcDatabaseContainer<?> jdbcDatabaseContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
                .withDatabaseName(databaseConfig.getDatabaseName())
                .withUsername(databaseConfig.getUsername())
                .withPassword(databaseConfig.getPassword())
                .withUrlParam("useSSL", "false")
                .withInitScript("sql-script/init_mysql.sql");
        mysqlTestContainer = jdbcDatabaseContainer;
        mysqlTestContainer.start();
        return R2dbcProtocol.builder()
                .databaseConfig(databaseConfig)
                .protocolSymbol("mysql")
                .host(jdbcDatabaseContainer.getHost())
                .port(jdbcDatabaseContainer.getMappedPort(MYSQL_PORT))
                .build();
    }

    @Override
    public boolean isRunning() {
        return mysqlTestContainer != null && mysqlTestContainer.isRunning();
    }

    @Override
    public void destroy() {
        if (mysqlTestContainer != null) {
            mysqlTestContainer.stop();
        }
    }
}
