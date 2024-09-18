package com.br.SambaWebAPI.testIntegration.logs;

import com.br.SambaWebAPI.SambaWebApiApplication;
import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.logs.repository.impl.LogRepositoryImpl;
import com.br.SambaWebAPI.logs.service.LogService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SambaWebApiApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogIntegrationTest {

    @Autowired
    private LogService logService;
    private LogRepositoryImpl logRepository;

    private static final String DATABASE_URL = "jdbc:h2:mem:testdb";
    private static final String DATABASE_USERNAME = "test";
    private static final String DATABASE_PASSWORD = "test";

    public void setup() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS LOGS (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "LOG_DESCRIPTION VARCHAR(255) NOT NULL" +
                    ")";
            try (PreparedStatement createTableStatement = connection.prepareStatement(createTableQuery)) {
                createTableStatement.execute();
            }
        }
    }

    @Test
    @DisplayName("""
                Give a log description,
                when insert log data,
                then the log description should be inserted
            """)
    public void insertLogData() throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        setup();

        String query = "INSERT INTO logs (log_description) VALUES ('Teste 1')";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
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
    @Test
    @DisplayName("""
                Give a empty log description,
                when try to insert log data,
                then the log description shouldn't be inserted
               """)
    public void testReadLogEmptyFile() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        setup();

        File logFile = new File("log.txt");
        if (!logFile.exists()) {
            logFile.createNewFile();
        }

        BufferedReader reader = new BufferedReader(new FileReader(logFile));
        String logDescription = "";
        String line;
        while ((line = reader.readLine()) != null) {
            logDescription += line + "\n";
        }
        reader.close();

        if (logDescription.trim().isEmpty()) {
            String query = "SELECT COUNT(*) FROM logs";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    assertEquals(0, count);
                }
            }
            return;
        }

        String query = "INSERT INTO logs (log_description) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, logDescription);
            statement.execute();
        }
    }

    @Test
    @DisplayName("""
            Give an invalid log file,
            when try to insert logs,
            then an exception should be thrown
        """)
    public void testInsertLogsInvalid() throws IOException {
        File logFile = new File("log.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile))) {
            writer.write("Invalid log entry");
        }
        LogService logService = new LogService(logRepository);
        try {
            logService.insertLogs();
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }
}