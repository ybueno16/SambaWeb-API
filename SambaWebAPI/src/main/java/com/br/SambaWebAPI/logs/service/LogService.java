package com.br.SambaWebAPI.logs.service;

import com.br.SambaWebAPI.logs.models.Log;
import com.br.SambaWebAPI.logs.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class LogService {
    private final LogRepository logRepository;
    final Log log;
    @Autowired
    public LogService(LogRepository logRepository, Log log) {
        this.logRepository = logRepository;

        this.log = log;
    }

    public boolean insertLog(Log log) throws Exception {
        return  logRepository.insertLog(log);
    }
}
