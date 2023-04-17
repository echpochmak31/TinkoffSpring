import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.tinkoff.edu.java.scrapper.dao.JdbcTemplateChatRepository;
import ru.tinkoff.edu.java.scrapper.dao.JdbcTemplateLinkRepository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.File;
import java.nio.file.Path;
import java.sql.*;


@Testcontainers
public abstract class IntegrationEnvironment {
    @Container
    static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;

    static Connection connection;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15"))
                .withDatabaseName("scrapper");

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

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public JdbcTemplateLinkRepository jdbcTemplateLinkRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcTemplateLinkRepository(jdbcTemplate);
        }

        @Bean
        public JdbcTemplateChatRepository jdbcTemplateChatRepository(NamedParameterJdbcTemplate jdbcTemplate){
            return new JdbcTemplateChatRepository(jdbcTemplate);
        }

        @Bean
        public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
            return new DataSourceTransactionManager(dataSource);
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
