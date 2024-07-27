package com.br.SambaWebAPI.user.controllers;

import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.password.services.PasswordService;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.user.services.UserService;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.user.exceptions.UserCreationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import static com.br.SambaWebAPI.config.Global.API_URL_SAMBA;



@RestController
@RequestMapping(API_URL_SAMBA + "/user-config")
public class UserController {
    final private ObjectMapper objectMapper;
    final private UserService userService;
    final private PasswordService passwordService;

    @Autowired
    public UserController(ObjectMapper objectMapper, UserService userService, PasswordService passwordService){
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.passwordService = passwordService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> UserCreation(@RequestBody Map<String, Object> json) {
        User user = objectMapper.convertValue(json.get("user"), User.class);
        SudoAuthentication sudoAuthentication = objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
        try {
            userService.createUser(user,sudoAuthentication);
            passwordService.createPassword(user,sudoAuthentication);

            return DefaultResponseEntityFactory.create("Usuario criado com sucesso!", user, HttpStatus.OK);
        } catch (UserCreationException e) {
            return DefaultResponseEntityFactory.create(e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            return DefaultResponseEntityFactory.create("Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário.", null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/user")
    public ResponseEntity<?> getUser(@RequestBody User user) {
        try{
            userService.getUser(user);
            return DefaultResponseEntityFactory.create("Usuario existe!", user, HttpStatus.OK);

        } catch (UserCreationException e) {
            return DefaultResponseEntityFactory.create(e.getErrorCode().getErrorMessage(),
                    null, e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            return DefaultResponseEntityFactory.create(
                    "Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário.",
                    null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/removeUser")
    public ResponseEntity<?> removeUser(@RequestBody Map<String, Object> json){
        User user = objectMapper.convertValue(json.get("user"), User.class);
        SudoAuthentication sudoAuthentication = objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);

        try {
            userService.removeUser(user,sudoAuthentication);
            return DefaultResponseEntityFactory.create("Usuario removido com sucesso!", user, HttpStatus.OK);
        } catch (UserCreationException e) {
            return DefaultResponseEntityFactory.create(e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            return DefaultResponseEntityFactory.create("Erro genérico. Ocorreu um erro desconhecido durante a remoção do usuário.", null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/createSambaUser")
    public ResponseEntity<?> createSambaUser(@RequestBody Map<String, Object> json){
        User user = objectMapper.convertValue(json.get("user"), User.class);
        SudoAuthentication sudoAuthentication = objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
        try {
            userService.createSambaUser(user,sudoAuthentication);
            return DefaultResponseEntityFactory.create("Usuario criado com sucesso!", user, HttpStatus.OK);
        } catch (UserCreationException e) {
            return DefaultResponseEntityFactory.create(e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            return DefaultResponseEntityFactory.create("Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário samba.", null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/removeSambaUser")
    public ResponseEntity<?> removeSambaUser(@RequestBody Map<String, Object> json){
        User user = objectMapper.convertValue(json.get("user"), User.class);
        SudoAuthentication sudoAuthentication = objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
        try {
            userService.removeSambaUser(user,sudoAuthentication);
            return DefaultResponseEntityFactory.create("Usuario removido com sucesso!", user, HttpStatus.OK);
        }  catch (Exception e) {
            return DefaultResponseEntityFactory.create("Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário samba.", null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
