package com.br.SambaWebAPI.logs.service;

import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.logs.repository.LogRepository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
      logRepository.insertLog(log[0]);
    }
    return true;
  }

  public List<String[]> readLog() throws Exception {
    List<String[]> logs = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(Global.SMB_LOG_PATH))) {
      String line;
      StringBuilder logEntry = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("[") && line.endsWith("]")) {
          if (!logEntry.isEmpty()) {
            logs.add(new String[] {logEntry.toString()});
            logEntry = new StringBuilder();
          }
          logEntry.append(line);
        } else {
          logEntry.append(" ").append(line.trim());
        }
      }
      if (!logEntry.isEmpty()) {
        logs.add(new String[] {logEntry.toString()});
      }
    } catch (IOException e) {
      throw new IOException("An IO error occurred while reading the log");
    } catch (Exception e) {
      throw new Exception("An error occurred while reading the log");
    }
    return logs;
  }
}
