package com.br.SambaWebAPI.logs.repository;

public interface LogRepository {
  public void insertLog(String logEntry) throws Exception;
}
