package com.br.SambaWebAPI.user.controllers;

import static com.br.SambaWebAPI.config.Global.API_URL_SAMBA;

//import com.br.SambaWebAPI.config.DefaultOperation;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.password.exceptions.CreatePasswordException;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.password.services.PasswordService;
import com.br.SambaWebAPI.user.exceptions.CreateUserException;
import com.br.SambaWebAPI.user.exceptions.DeleteUserException;
import com.br.SambaWebAPI.user.exceptions.CreateUserSambaException;
import com.br.SambaWebAPI.user.exceptions.UserSambaDeleteException;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.user.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API_URL_SAMBA + "/user-config")
public class UserController {
  private final ObjectMapper objectMapper;
  private final UserService userService;
  private final PasswordService passwordService;

  @Autowired
  public UserController(
          ObjectMapper objectMapper, UserService userService, PasswordService passwordService) {
    this.objectMapper = objectMapper;
    this.userService = userService;
    this.passwordService = passwordService;
  }

  @PostMapping
//  @DefaultOperation(summary = "Buscar", description = "Buscar cliente pelo nome", tags = {"Cliente"})
  public ResponseEntity<?> UserCreation( @RequestBody Map<String, Object> json) throws Exception {
    User user = objectMapper.convertValue(json.get("user"), User.class);
    SudoAuthentication sudoAuthentication =
            objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
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

  @DeleteMapping
  public ResponseEntity<?> removeUser(@RequestBody Map<String, Object> json) {
    User user = objectMapper.convertValue(json.get("user"), User.class);
    SudoAuthentication sudoAuthentication =
            objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);

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

  @PostMapping(path = "/createSambaUser")
  public ResponseEntity<?> createSambaUser(@RequestBody Map<String, Object> json)
          throws CreateUserSambaException {
    User user = objectMapper.convertValue(json.get("user"), User.class);
    SudoAuthentication sudoAuthentication =
            objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
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

  @DeleteMapping(path = "/removeSambaUser")
  public ResponseEntity<?> removeSambaUser(@RequestBody Map<String, Object> json)
          throws UserSambaDeleteException {
    User user = objectMapper.convertValue(json.get("user"), User.class);
    SudoAuthentication sudoAuthentication =
            objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
    try {
      userService.removeSambaUser(user, sudoAuthentication);
      return DefaultResponseEntityFactory.create(
              "Smba user removed successfully!", user, HttpStatus.OK);
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
