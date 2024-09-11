package com.br.SambaWebAPI.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {
  @Value("${postgres.driver}")
  private String driver;

  @Value("${postgres.ip}")
  private String ip;

  @Value("${postgres.port}")
  private String port;

  @Value("${postgres.database}")
  private String database;

  @Value("${postgres.username}")
  private String username;

  @Value("${postgres.password}")
  private String password;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl(driver + "://" + ip + ":" + port + "/" + database);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }
}
