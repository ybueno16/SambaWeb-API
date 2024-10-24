package com.br.SambaWebAPI.serverconfig.controller;

import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.config.swagger.DefaultOperation;
import com.br.SambaWebAPI.serverconfig.models.ServerConfig;
import com.br.SambaWebAPI.serverconfig.service.ServerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Global.API_URL_SAMBA + "/serverconfig")
public class ServerConfigController {

  private final ServerConfigService serverConfigService;

  @Autowired
  public ServerConfigController(ServerConfigService serverConfigService) {
    this.serverConfigService = serverConfigService;
  }

  @PostMapping
  @DefaultOperation(
      summary = "Save server config",
      description = "Save server config in the database",
      tags = {"Server Config"})
  public ResponseEntity<?> saveServerConfig(@RequestBody ServerConfig serverConfig) {
    try {
      boolean retorno = serverConfigService.saveServerConfig(serverConfig);
      return DefaultResponseEntityFactory.create(
          "Server config was inserted successfully!", retorno, HttpStatus.OK);
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
          "Error saving server config.", null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
