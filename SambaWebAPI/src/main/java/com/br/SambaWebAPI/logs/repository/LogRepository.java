package com.br.SambaWebAPI.logs.repository;

import com.br.SambaWebAPI.logs.models.Log;

import java.util.List;

public interface LogRepository {
    public boolean insertLog(Log log) throws Exception;

}
