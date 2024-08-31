package com.br.SambaWebAPI.testIntegration.logs;

import com.br.SambaWebAPI.SambaWebApiApplication;
import com.br.SambaWebAPI.config.DataSourceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SambaWebApiApplication.class)
@TestPropertySource(locations = "classpath:applicationTest.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogIntegrationTest {

    @Autowired
    private DataSourceConfig dataSourceConfig;


    @Test
    public void insertLogData() throws SQLException {
        try (Connection connection = dataSourceConfig.dataSource().getConnection()) {

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