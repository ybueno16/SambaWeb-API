package com.br.SambaWebAPI.testIntegration.logs;

import com.br.SambaWebAPI.SambaWebApiApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SambaWebApiApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogIntegrationTest {

    private static final String DATABASE_URL = "jdbc:h2:mem:testdb";
    private static final String DATABASE_USERNAME = "test";
    private static final String DATABASE_PASSWORD = "test";

    @Test
    public void insertLogData() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS LOGS (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "LOG_DESCRIPTION VARCHAR(255) NOT NULL" +
                    ")";
            try (PreparedStatement createTableStatement = connection.prepareStatement(createTableQuery)) {
                createTableStatement.execute();
            }

            String query = "INSERT INTO logs (log_description) VALUES ('Teste 1')";

            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.execute();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        query = "SELECT log_description FROM logs WHERE id = ?";

                        try (PreparedStatement selectStatement = connection.prepareStatement(query)) {
                            selectStatement.setInt(1, id);

                            try (ResultSet resultSet = selectStatement.executeQuery()) {
                                while (resultSet.next()) {
                                    String logDescription = resultSet.getString("log_description");
                                    assertEquals("Teste 1", logDescription);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}