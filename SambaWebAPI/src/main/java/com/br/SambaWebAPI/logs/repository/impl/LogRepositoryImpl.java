package com.br.SambaWebAPI.logs.repository.impl;

import com.br.SambaWebAPI.logs.repository.LogRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LogRepositoryImpl implements LogRepository {

  StringBuilder sql = null;
  private final DataSource dataSource;

  @Autowired
  public LogRepositoryImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void insertLog(String logEntry) throws Exception {
    try (Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement()) {
      StringBuilder sql = new StringBuilder();
      sql.append("INSERT INTO public.logs \n")
          .append("(log_description) \n")
          .append("VALUES(")
          .append("'")
          .append(logEntry)
          .append("'")
          .append(")");
      stmt.execute(sql.toString());
    } catch (SQLException e) {
      throw new SQLException("There was an error when inserting into the database.");
    }
  }
}
