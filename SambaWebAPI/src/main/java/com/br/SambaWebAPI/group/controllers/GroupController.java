package com.br.SambaWebAPI.group.controllers;

import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.group.exceptions.AddUserToGroupException;
import com.br.SambaWebAPI.group.exceptions.GroupCreationException;
import com.br.SambaWebAPI.group.models.Group;
import com.br.SambaWebAPI.group.services.GroupService;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Global.API_URL_SAMBA + "/group-config")
public class GroupController {

  private final ObjectMapper objectMapper;
  private final GroupService groupService;

  @Autowired
  public GroupController(ObjectMapper objectMapper, GroupService groupService) {
    this.objectMapper = objectMapper;
    this.groupService = groupService;
  }

  @PostMapping(path = "/registerGroup")
  public ResponseEntity<?> GroupCreation(@RequestBody Map<String, Object> json) {
    Group group = objectMapper.convertValue(json.get("group"), Group.class);
    SudoAuthentication sudoAuthentication =
        objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
    try {
      groupService.createGroup(group, sudoAuthentication);

      return DefaultResponseEntityFactory.create("Grupo criado com sucesso!", group, HttpStatus.OK);
    } catch (GroupCreationException e) {
      return DefaultResponseEntityFactory.create(
          e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
          "Erro genérico. Ocorreu um erro desconhecido durante a criação do grupo.",
          null,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(path = "/assignUserToGroup")
  public ResponseEntity<?> GroupConfig(@RequestBody Map<String, Object> json) {
    User user = objectMapper.convertValue(json.get("user"), User.class);
    Group group = objectMapper.convertValue(json.get("group"), Group.class);
    SudoAuthentication sudoAuthentication =
        objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);

    try {
      groupService.addUserToGroup(group, user, sudoAuthentication);

      return DefaultResponseEntityFactory.create(
          "Grupo associado ao usuario com sucesso!", user, HttpStatus.OK);
    } catch (AddUserToGroupException e) {
      return DefaultResponseEntityFactory.create(
          e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
          "Erro genérico. Ocorreu um erro desconhecido durante a criação do grupo.",
          null,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping(path = "/deleteGroup")
  public ResponseEntity<?> deleteGroup(@RequestBody Map<String, Object> json) {
    Group group = objectMapper.convertValue(json.get("group"), Group.class);
    SudoAuthentication sudoAuthentication =
        objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);

    try {
      groupService.deleteGroup(group, sudoAuthentication);

      return DefaultResponseEntityFactory.create(
          "Grupo removido com sucesso!", group, HttpStatus.OK);
    } catch (AddUserToGroupException e) {
      return DefaultResponseEntityFactory.create(
          e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
          "Erro genérico. Ocorreu um erro desconhecido durante a remoção do grupo.",
          null,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
