package com.br.SambaWebAPI.logs.controller;

import com.br.SambaWebAPI.config.DefaultOperation;
import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.logs.service.LogService;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Global.API_URL_SAMBA + "/log")
public class LogController {

  private final LogService logService;

  @Autowired
  public LogController(LogService logService) {
    this.logService = logService;
  }

  @PostMapping
  @DefaultOperation(
          summary = "Save logs",
          description = "Save logs from samba in the database",
          tags = {"Logs"})
  public ResponseEntity<?> writeSambaFile() throws Exception {
    try {
      boolean retorno = logService.insertLogs();
      return DefaultResponseEntityFactory.create(
          "Log was inserted successfully!", retorno, HttpStatus.OK);
    } catch (SQLException e) {
      return DefaultResponseEntityFactory.create(
          "There was an error while inserting it into the database.", null, HttpStatus.INTERNAL_SERVER_ERROR);

    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
          "Error saving logs.", null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
