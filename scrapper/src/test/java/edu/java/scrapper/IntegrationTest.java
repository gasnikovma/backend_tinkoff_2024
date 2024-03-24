package edu.java.scrapper;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers public  class IntegrationTest {
    public static PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:16").withDatabaseName("scrapper").withUsername("postgres")
            .withPassword("postgres");
        POSTGRES.start();
        try {
            runMigrations(POSTGRES);
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void runMigrations(JdbcDatabaseContainer<?> c) throws SQLException, LiquibaseException {
        String url = c.getJdbcUrl();
        String login = c.getUsername();
        String password = c.getPassword();
        try (JdbcConnection jdbcConnection = new JdbcConnection(DriverManager.getConnection(url, login, password))) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            Liquibase liquibase =
                new liquibase.Liquibase("migrations/master.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());

        }
    }

    @DynamicPropertySource static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Test void testDatabaseConnection() throws SQLException {

        String jdbcUrl = POSTGRES.getJdbcUrl();
        String username = POSTGRES.getUsername();
        String password = POSTGRES.getPassword();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            PreparedStatement insertRequest = connection.prepareStatement("INSERT INTO Chat(tg_chat_id) VALUES (?)");
            insertRequest.setInt(1, 61);
            insertRequest.execute();
            PreparedStatement selectRequest = connection.prepareStatement("SELECT tg_chat_id FROM Chat Where id =(?)");
            selectRequest.setInt(1, 1);
            selectRequest.execute();
            ResultSet resultSet = selectRequest.getResultSet();
            resultSet.next();
            assertEquals(resultSet.getInt(1), 61);

        }

    }
}
