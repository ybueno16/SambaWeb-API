package com.br.SambaWebAPI.user.controllers;

import static com.br.SambaWebAPI.config.Global.API_URL_SAMBA;

//import com.br.SambaWebAPI.config.swagger.DefaultOperation;
import com.br.SambaWebAPI.config.swagger.DefaultOperation;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.password.exceptions.CreatePasswordException;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.password.services.PasswordService;
import com.br.SambaWebAPI.user.exceptions.CreateUserException;
import com.br.SambaWebAPI.user.exceptions.DeleteUserException;
import com.br.SambaWebAPI.user.exceptions.CreateUserSambaException;
import com.br.SambaWebAPI.user.exceptions.UserSambaDeleteException;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.user.models.dto.UserOperationsDTO;
import com.br.SambaWebAPI.user.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API_URL_SAMBA + "/user-config")
public class UserController {
  private final UserService userService;
  private final PasswordService passwordService;

  @Autowired
  public UserController(UserService userService,
                        PasswordService passwordService) {
    this.userService = userService;
    this.passwordService = passwordService;
  }

  @PostMapping
  @DefaultOperation(summary = "Create User", description = "Create a new linux user", tags = {"User"})
  public ResponseEntity<?> UserCreation(@RequestBody UserOperationsDTO request) throws Exception {
    User user = request.getUser();
    SudoAuthentication sudoAuthentication = request.getSudoAuthentication();
    try {
      userService.createUser(user, sudoAuthentication);
      passwordService.createPassword(user);
      return DefaultResponseEntityFactory.create(
              "User created successfully!", user, HttpStatus.OK);
    } catch (CreatePasswordException e) {
      userService.removeUser(user, sudoAuthentication);
      return ResponseEntity.badRequest().build();
    } catch (CreateUserException e) {
      return DefaultResponseEntityFactory.create(
              e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
    } catch (DeleteUserException e) {
      return DefaultResponseEntityFactory.create(
              e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
              "Generic error. An unknown error occurred during user creation.",
              null,
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DefaultOperation(summary = "List User", description = "Try to list a existent user", tags = {"User"})

  @PostMapping(path = "/getUser")
  public ResponseEntity<?> getUser(@RequestBody User user) {
    try {
      userService.getUser(user);
      return DefaultResponseEntityFactory.create("User already exists!", user, HttpStatus.OK);

    } catch (CreateUserException e) {
      return DefaultResponseEntityFactory.create(
              e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
              "Generic error. An unknown error occurred during user creation.",
              null,
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @DefaultOperation(summary = "Delete User", description = "Delete user", tags = {"User"})
  @DeleteMapping
  public ResponseEntity<?> removeUser(@RequestBody UserOperationsDTO request) {
    User user = request.getUser();
    SudoAuthentication sudoAuthentication = request.getSudoAuthentication();

    try {
      userService.removeUser(user, sudoAuthentication);
      return DefaultResponseEntityFactory.create(
              "User removed successfully!", user, HttpStatus.OK);
    } catch (DeleteUserException e) {
      return DefaultResponseEntityFactory.create(
              e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
              "Generic error. An unknown error occurred while removing the user.",
              null,
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @DefaultOperation(summary = "Create user", description = "Create samba user", tags = {"User"})

  @PostMapping(path = "/createSambaUser")
  public ResponseEntity<?> createSambaUser(@RequestBody UserOperationsDTO request)
          throws CreateUserSambaException {
    User user = request.getUser();
    SudoAuthentication sudoAuthentication = request.getSudoAuthentication();
    try {
      userService.createSambaUser(user, sudoAuthentication);
      return DefaultResponseEntityFactory.create(
              "Samba user created successfully!", user, HttpStatus.OK);
    } catch (CreateUserSambaException e) {
      return DefaultResponseEntityFactory.create(
              e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
    } catch (Exception e) {
      return DefaultResponseEntityFactory.create(
              "Generic error. An unknown error occurred while creating the samba user.",
              null,
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DefaultOperation(summary = "Delete User", description = "Delete samba user", tags = {"User"})

  @DeleteMapping(path = "/removeSambaUser")
  public ResponseEntity<?> removeSambaUser(@RequestBody UserOperationsDTO request)
          throws UserSambaDeleteException {
    User user = request.getUser();
    SudoAuthentication sudoAuthentication = request.getSudoAuthentication();
    try {
      userService.removeSambaUser(user, sudoAuthentication);
      return DefaultResponseEntityFactory.create(
              "Samba user removed successfully!", user, HttpStatus.OK);
    } catch (UserSambaDeleteException e) {
      return DefaultResponseEntityFactory.create(
              e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
    }
    catch (Exception e) {
      return DefaultResponseEntityFactory.create(
              "Generic error. An unknown error occurred while removing the samba user.",
              null,
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
