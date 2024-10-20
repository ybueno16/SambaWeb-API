package com.br.SambaWebAPI.group.controllers;

import com.br.SambaWebAPI.config.swagger.DefaultOperation;
import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.folder.models.dto.FolderOperationsDTO;
import com.br.SambaWebAPI.group.exceptions.AddUserToGroupException;
import com.br.SambaWebAPI.group.exceptions.CreateGroupException;
import com.br.SambaWebAPI.group.exceptions.DeleteGroupException;
import com.br.SambaWebAPI.group.models.Group;
import com.br.SambaWebAPI.group.models.dto.AssignUserToGroupDTO;
import com.br.SambaWebAPI.group.models.dto.GroupOperationDTO;
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

  private final GroupService groupService;

  @Autowired
  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @PostMapping
  @DefaultOperation(
          summary = "Add Group",
          description = "Create linux group",
          tags = {"Group"})
  public ResponseEntity<?> groupCreation(@RequestBody GroupOperationDTO request) {
    Group group = request.getGroup();
    SudoAuthentication sudoAuthentication = request.getSudoAuthentication();
    try {
      groupService.createGroup(group, sudoAuthentication);

      return DefaultResponseEntityFactory.create("Group created successfully!", group, HttpStatus.OK);
    } catch (CreateGroupException e) {
      return DefaultResponseEntityFactory.create(
              e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
              "Generic error. An error occurred while creating the group.",
              null,
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(path = "/assignUserToGroup")
  @DefaultOperation(
          summary = "Assign User to Group",
          description = "Assign a user to an group",
          tags = {"Group"})
  public ResponseEntity<?> addUserToGroup(@RequestBody AssignUserToGroupDTO request) {
    Group group = request.getGroup();
    SudoAuthentication sudoAuthentication = request.getSudoAuthentication();
    User user = request.getUser();

    try {
      groupService.addUserToGroup(group, user, sudoAuthentication);

      return DefaultResponseEntityFactory.create(
              "Group successfully associated with the user!", user, HttpStatus.OK);
    } catch (AddUserToGroupException e) {
      return DefaultResponseEntityFactory.create(
              e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
              "Generic error. An unknown error occurred while associating the group with the user.",
              null,
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping
  @DefaultOperation(
          summary = "Delete Group",
          description = "Delete linux group",
          tags = {"Group"})
  public ResponseEntity<?> deleteGroup(@RequestBody AssignUserToGroupDTO request) {
    Group group = request.getGroup();
    SudoAuthentication sudoAuthentication = request.getSudoAuthentication();

    try {
      groupService.deleteGroup(group, sudoAuthentication);

      return DefaultResponseEntityFactory.create(
              "Group removed successfully!", group, HttpStatus.OK);
    } catch (DeleteGroupException e) {
      return DefaultResponseEntityFactory.create(
              e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
              "Generic error. An unknown error occurred while removing the group.",
              null,
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
