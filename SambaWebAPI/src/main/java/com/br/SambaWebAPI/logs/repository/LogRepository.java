package com.br.SambaWebAPI.logs.repository;

public interface LogRepository {
    public boolean insertLog(String date, String hour, String logDescription) throws Exception;

}
