package com.br.SambaWebAPI.folder.controllers;

import com.br.SambaWebAPI.config.swagger.DefaultOperation;
import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.folder.models.dto.FolderOperationsDTO;
import com.br.SambaWebAPI.folder.services.FolderService;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.permission.services.PermissionService;
import com.br.SambaWebAPI.user.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Global.API_URL_SAMBA + "/folder-config")
public class FolderController {

  private final FolderService folderService;

  @Autowired
  public FolderController(FolderService folderService) {
    this.folderService = folderService;
  }

  @PostMapping
  @DefaultOperation(
          summary = "Create folder",
          description = "Create an folder",
          tags = {"Folder"})
  public ResponseEntity<?> folderCreate(@RequestBody FolderOperationsDTO request) {
    Folder folder = request.getFolder();
    SudoAuthentication sudoAuthentication = request.getSudoAuthentication();
    try {
      folderService.createFolder(folder, sudoAuthentication);
      return DefaultResponseEntityFactory.create(
              "Folder created successfully!", folder, HttpStatus.OK);
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
              "Generic error. An error occurred while creating the folder.",
              null,
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping
  @DefaultOperation(
          summary = "Delete folder",
          description = "Delete an folder",
          tags = {"Folder"})
  public ResponseEntity<?> removeFolder(@RequestBody FolderOperationsDTO request) {
    Folder folder = request.getFolder();
    SudoAuthentication sudoAuthentication = request.getSudoAuthentication();
    try {
      folderService.removeFolder(folder, sudoAuthentication);
      return DefaultResponseEntityFactory.create(
              "Folder deleted successfully!", folder, HttpStatus.OK);
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
              "Generic error. An error occurred while removing the folder.",
              null,
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
