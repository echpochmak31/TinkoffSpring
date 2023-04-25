import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Testcontainers
public abstract class IntegrationEnvironment {
    @Container
    static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;

    static Connection connection;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15"))
                .withDatabaseName("scrapper")
                .withExposedPorts(5432, 5433);

        POSTGRE_SQL_CONTAINER.start();

    }

    @Configuration
    static class IntegrationEnvironmentConfig {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url(POSTGRE_SQL_CONTAINER.getJdbcUrl())
                    .username(POSTGRE_SQL_CONTAINER.getUsername())
                    .password(POSTGRE_SQL_CONTAINER.getPassword())
                    .build();
        }
    }

    @BeforeAll
    static void liquibaseSetup() throws SQLException, LiquibaseException {
        String url = POSTGRE_SQL_CONTAINER.getJdbcUrl();
        String user = POSTGRE_SQL_CONTAINER.getUsername();
        String password = POSTGRE_SQL_CONTAINER.getPassword();

        connection = DriverManager.getConnection(url, user, password);

        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));

        String scrapperProjectPath = new File("").getAbsolutePath();
        Path projectPath = new File(scrapperProjectPath).toPath().getParent();
        File searchPath = projectPath.resolve("migrations").toFile();

        Liquibase liquibase = new liquibase.Liquibase(
                "master.xml",
                new FileSystemResourceAccessor(searchPath),
                database
        );

        liquibase.update(new Contexts(), new LabelExpression());
    }
}
