package com.br.SambaWebAPI.permission.controller;

import com.br.SambaWebAPI.config.swagger.DefaultOperation;
import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.group.models.dto.GroupOperationDTO;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.permission.exceptions.PermissionAddException;
import com.br.SambaWebAPI.permission.models.GroupPermission;
import com.br.SambaWebAPI.permission.models.OwnerPermission;
import com.br.SambaWebAPI.permission.models.PublicPermission;
import com.br.SambaWebAPI.permission.models.dto.PermissionDTO;
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
@RequestMapping(Global.API_URL_SAMBA + "/permission-config")
public class PermissionController {

  private final ObjectMapper objectMapper;
  private final PermissionService permissionService;

  @Autowired
  public PermissionController(ObjectMapper objectMapper, PermissionService permissionService) {
    this.objectMapper = objectMapper;
    this.permissionService = permissionService;
  }

  @PostMapping(path = "managePermission")
  @DefaultOperation(
          summary = "Add Permission",
          description = "Add permission to a folder or file",
          tags = {"Permission"})
  public ResponseEntity<?> permissionManager(@RequestBody PermissionDTO request) {
    OwnerPermission ownerPermission = request.getOwnerPermission();
    GroupPermission groupPermission = request.getGroupPermission();
    PublicPermission publicPermission = request.getPublicPermission();
    Folder folder = request.getFolder();
    SudoAuthentication sudoAuthentication = request.getSudoAuthentication();

    try {
      permissionService.managePermission(
              ownerPermission, groupPermission, publicPermission, sudoAuthentication, folder);
      return DefaultResponseEntityFactory.create(
              "Permission granted correctly!", folder, HttpStatus.OK);
    } catch (PermissionAddException e) {
      return DefaultResponseEntityFactory.create(
              e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
              "Generic error. An unknown error occurred during group creation.",
              null,
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
