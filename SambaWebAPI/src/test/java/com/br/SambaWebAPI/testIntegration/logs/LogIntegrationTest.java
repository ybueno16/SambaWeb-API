package com.br.SambaWebAPI.testIntegration.logs;

import com.br.SambaWebAPI.SambaWebApiApplication;
import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.logs.repository.impl.LogRepositoryImpl;
import com.br.SambaWebAPI.logs.service.LogService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
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

    private DataSource dataSource;

    @BeforeAll
    public void setupDataSource() {
        dataSource = DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url(DATABASE_URL)
                .username(DATABASE_USERNAME)
                .password(DATABASE_PASSWORD)
                .build();
    }


    public void setup() throws SQLException {
        logRepository = new LogRepositoryImpl(dataSource);
        try (Connection connection = dataSource.getConnection()) {
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
    public void insertLogData() throws Exception {

        LogRepositoryImpl logRepository = new LogRepositoryImpl(dataSource);
        logRepository.insertLog("Teste 1");

        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        String query = "SELECT log_description FROM logs WHERE log_description = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(query)) {
            selectStatement.setString(1, "Teste 1");
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    String logDescription = resultSet.getString("log_description");
                    assertEquals("Teste 1", logDescription);
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
    public void testReadLogEmptyFile() throws Exception {
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
            try (Statement statement = dataSource.getConnection().createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    assertEquals(0, count);
                }
            }
            return;
        }
        logRepository.insertLog(logDescription);
    }

    @Test
    @DisplayName("""
        Give an invalid log file,
        when try to insert logs,
        then an exception should be thrown
    """)
    public void testInsertLogsInvalid() throws Exception {

        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:invalid-db")
                .username("test")
                .password("test")
                .build();

        LogRepositoryImpl logRepository = new LogRepositoryImpl(dataSource);

        File logFile = new File("log.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile))) {
            writer.write("Invalid log entry");
        }
        LogService logService = new LogService(logRepository);
        try {
            logService.insertLogs();
            fail("Expected an exception to be thrown");
        } catch (IOException e) {
            assertNotNull(e);
        }
    }
}