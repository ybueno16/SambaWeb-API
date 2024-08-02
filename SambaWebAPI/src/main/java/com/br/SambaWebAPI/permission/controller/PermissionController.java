package com.br.SambaWebAPI.permission.controller;

import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.permission.exceptions.PermissionAddException;
import com.br.SambaWebAPI.permission.models.GroupPermission;
import com.br.SambaWebAPI.permission.models.OwnerPermission;
import com.br.SambaWebAPI.permission.models.PublicPermission;
import com.br.SambaWebAPI.permission.services.PermissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping(Global.API_URL_SAMBA + "/permission-config")
public class PermissionController {

    final private ObjectMapper objectMapper;
    final private PermissionService permissionService;

    @Autowired
    public PermissionController(ObjectMapper objectMapper, PermissionService permissionService) {
        this.objectMapper = objectMapper;
        this.permissionService = permissionService;
    }

    @PostMapping(path = "managePermission")
    public ResponseEntity<?> permissionManager(@RequestBody Map<String, Map<String, String>> json){
        OwnerPermission ownerPermission = objectMapper.convertValue(json.get("ownerPermission"), OwnerPermission.class);
        GroupPermission groupPermission = objectMapper.convertValue(json.get("groupPermission"), GroupPermission.class);
        PublicPermission publicPermission = objectMapper.convertValue(json.get("publicPermission"), PublicPermission.class);
        Folder folder = objectMapper.convertValue(json.get("folder"), Folder.class);
        SudoAuthentication sudoAuthentication = objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
        try {
            permissionService.managePermission(ownerPermission,groupPermission,publicPermission,sudoAuthentication,folder);
            return DefaultResponseEntityFactory.create("Garantido a permissão correta!",folder, HttpStatus.OK);
        } catch (PermissionAddException e) {
            return DefaultResponseEntityFactory.create(e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            return DefaultResponseEntityFactory.create("Erro genérico. Ocorreu um erro desconhecido durante a criação do grupo.", null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
