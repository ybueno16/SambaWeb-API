package com.br.SambaWebAPI.folder.controllers;

import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.folder.services.FolderService;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.permission.services.PermissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Global.API_URL_SAMBA + "/folder-config")
public class FolderController {

  private final ObjectMapper objectMapper;
  private final FolderService folderService;
  private final PermissionService permissionService;

  @Autowired
  public FolderController(
      ObjectMapper objectMapper, FolderService folderService, PermissionService permissionService) {
    this.objectMapper = objectMapper;
    this.folderService = folderService;
    this.permissionService = permissionService;
  }

  @PostMapping(path = "/createFolder")
  public ResponseEntity<?> folderCreation(@RequestBody Map<String, Object> json) {
    Folder folder = objectMapper.convertValue(json.get("folder"), Folder.class);
    SudoAuthentication sudoAuthentication =
        objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);

    try {
      folderService.createFolder(folder, sudoAuthentication);
      return DefaultResponseEntityFactory.create(
          "Pasta criada com sucesso!", folder, HttpStatus.OK);
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
          "Erro genérico. Ocorreu um erro desconhecido durante a criação da pasta.",
          null,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
