package com.br.SambaWebAPI.sambaconfig.controller;

import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.config.swagger.DefaultOperation;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.sambaconfig.models.SambaConfig;
import com.br.SambaWebAPI.sambaconfig.services.SambaConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Global.API_URL_SAMBA + "/samba-config")
public class SambaConfigController {
  private final ObjectMapper objectMapper;
  private final SambaConfigService sambaConfigService;

  @Autowired
  public SambaConfigController(ObjectMapper objectMapper, SambaConfigService sambaConfigService) {
    this.objectMapper = objectMapper;
    this.sambaConfigService = sambaConfigService;
  }

  @PostMapping(path = "/writeSambaFile")
  @DefaultOperation(
      summary = "Add New Config",
      description = "Create a new param section in samba config",
      tags = {"Samba Config"})
  public ResponseEntity<?> writeSambaFile(@RequestBody Map<String, Object> json) {
    SambaConfig sambaConfig = objectMapper.convertValue(json.get("sambaConfig"), SambaConfig.class);
    SudoAuthentication sudoAuthentication =
        objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
    try {
      sambaConfigService.sambaConfigWriteNewConfig(sambaConfig, sudoAuthentication);
      sambaConfigService.refreshSambaConfig(sudoAuthentication);
      return DefaultResponseEntityFactory.create(
          "Configuration saved successfully!", sambaConfig, HttpStatus.OK);

    } catch (IOException e) {
      return DefaultResponseEntityFactory.create(
          "An error occurred while writing to the file. " + e,
          null,
          HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
          "Generic error. An unknown error occurred while writing to the file."
              + Global.SMB_CONF_PATH
              + " "
              + e,
          null,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DefaultOperation(
      summary = "Edit Config",
      description = "Edit params os a section in samba config",
      tags = {"Samba Config"})
  @PatchMapping(path = "/editSambaFile")
  public ResponseEntity<?> editSambaFile(@RequestBody Map<String, Object> json) {
    SambaConfig sambaConfig = objectMapper.convertValue(json.get("sambaConfig"), SambaConfig.class);
    SudoAuthentication sudoAuthentication =
        objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
    try {
      sambaConfigService.sambaConfigEditConfig(sambaConfig, sudoAuthentication);
      sambaConfigService.refreshSambaConfig(sudoAuthentication);
      return DefaultResponseEntityFactory.create(
          "Configuration saved successfully!", sambaConfig, HttpStatus.OK);

    } catch (IOException e) {
      return DefaultResponseEntityFactory.create(
          "An error occurred while writing to the file. " + e,
          null,
          HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
          "Generic error. An unknown error occurred while writing to the file."
              + Global.SMB_CONF_PATH
              + " "
              + e,
          null,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DefaultOperation(
      summary = "Delete Config",
      description = "Remove a entire param section in samba config",
      tags = {"Samba Config"})
  @DeleteMapping(path = "/sectionParams")
  public ResponseEntity<?> removeSectionParamsSamba(@RequestBody Map<String, Object> json) {
    SambaConfig sambaConfig = objectMapper.convertValue(json.get("sambaConfig"), SambaConfig.class);
    SudoAuthentication sudoAuthentication =
        objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
    try {
      sambaConfigService.sambaConfigRemoveSectionParams(sambaConfig, sudoAuthentication);
      sambaConfigService.refreshSambaConfig(sudoAuthentication);

      return DefaultResponseEntityFactory.create(
          "Configuration saved successfully!", sambaConfig, HttpStatus.OK);

    } catch (IOException e) {
      return DefaultResponseEntityFactory.create(
          "An error occurred while writing to the file. " + e,
          null,
          HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
          "Generic error. An unknown error occurred while writing to the file."
              + Global.SMB_CONF_PATH
              + " "
              + e,
          null,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DefaultOperation(
      summary = "Delete Config",
      description = "Remove a entire param in samba config",
      tags = {"Samba Config"})
  @DeleteMapping(path = "/section")
  public ResponseEntity<?> removeSectionSamba(@RequestBody Map<String, Object> json) {
    SambaConfig sambaConfig = objectMapper.convertValue(json.get("sambaConfig"), SambaConfig.class);
    SudoAuthentication sudoAuthentication =
        objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
    try {
      sambaConfigService.sambaConfigRemoveSection(sambaConfig, sudoAuthentication);
      sambaConfigService.refreshSambaConfig(sudoAuthentication);

      return DefaultResponseEntityFactory.create(
          "Configuration saved successfully!", sambaConfig, HttpStatus.OK);

    } catch (IOException e) {
      return DefaultResponseEntityFactory.create(
          "An error occurred while writing to the file. " + e,
          null,
          HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
          "Generic error. An unknown error occurred while writing to the file."
              + Global.SMB_CONF_PATH
              + " "
              + e,
          null,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
