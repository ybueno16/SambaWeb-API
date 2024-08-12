package com.br.SambaWebAPI.logs.service;

import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.logs.models.Log;
import com.br.SambaWebAPI.logs.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LogService {
    private final LogRepository logRepository;
    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;

    }


    public boolean insertLogs() throws Exception {
        List<String[]> logs = readLog();
        for (String[] log : logs) {
            logRepository.insertLog(log[0], log[1], log[2]);
        }
        return true;
    }

    public List<String[]> readLog() throws IOException {
        List<String[]> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(Global.SMB_LOG_PATH))) {
            String line;
            while ((line = reader.readLine())!= null) {
                String[] parts = line.split("  ", 2); // separa a linha em duas partes: data/hora e descrição
                if (parts.length == 2) {
                    String[] dateHour = parts[0].split(", ", 2); // separa data e hora
                    if (dateHour.length >= 2) { // adiciona essa verificação
                        String[] log = new String[3];
                        log[0] = dateHour[0].substring(1, 11); // extrai a data (YYYY/MM/DD)
                        log[1] = dateHour[1].substring(0, 8); // extrai a hora (HH:MM:SS)
                        log[2] = parts[1].trim(); // extrai a descrição do log
                        logs.add(log);
                        System.out.println(logs);
                        System.out.println(line);
                    }

                }
            }
        } catch (Exception e){
            System.out.println(e);
            throw e;
        }
        return logs;
    }
}
