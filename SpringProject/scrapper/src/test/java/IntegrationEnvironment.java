import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.nio.file.Path;
import java.sql.*;

@Testcontainers
abstract class AbstractContainerBaseTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15"))
                .withDatabaseName("scrapper");

        POSTGRE_SQL_CONTAINER.start();

    }
}

public class IntegrationEnvironment extends AbstractContainerBaseTest {

    private boolean tableExists(Connection connection, String schema, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, schema, tableName, new String[] {"TABLE"});

        return resultSet.next();
    }

    @Test
    void Should_CreateTables_When_SetupLiquibase() throws SQLException, LiquibaseException {

        String url = POSTGRE_SQL_CONTAINER.getJdbcUrl();
        String user = POSTGRE_SQL_CONTAINER.getUsername();
        String password = POSTGRE_SQL_CONTAINER.getPassword();

        java.sql.Connection connection = DriverManager.getConnection(url, user, password);

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

        Assertions.assertAll(
                () -> Assertions.assertNotNull(url),
                () -> Assertions.assertTrue(tableExists(connection, "links", "link")),
                () -> Assertions.assertTrue(tableExists(connection, "links","chat")),
                () -> Assertions.assertTrue(tableExists(connection, "links", "link_chat"))
        );
    }
}
