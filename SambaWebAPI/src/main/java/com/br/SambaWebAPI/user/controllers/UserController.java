package com.br.SambaWebAPI.user.controllers;

import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.password.services.PasswordService;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.user.services.UserService;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.user.exceptions.UserCreationException;
import com.br.SambaWebAPI.user.factory.UserCreationFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.br.SambaWebAPI.config.Global.API_URL_SAMBA;

@RestController
@RequestMapping(API_URL_SAMBA + "/user-config")
public class UserController {
    ObjectMapper objectMapper = new ObjectMapper();
    UserService userService = new UserService();
    PasswordService passwordService = new PasswordService();

    @PostMapping(path = "/register")
    public ResponseEntity<?> InitialUserCreation(@RequestBody Map<String, Object> json) throws UserCreationFactory {
        User user = objectMapper.convertValue(json.get("user"), User.class);
        SudoAuthentication sudoAuthentication = objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
        try {
            userService.createUser(user,sudoAuthentication);
            passwordService.createPassword(user);

            return DefaultResponseEntityFactory.create("Usuario criado com sucesso!", user, HttpStatus.OK);
        } catch (UserCreationException e) {
            return DefaultResponseEntityFactory.create(e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            return DefaultResponseEntityFactory.create("Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário.", null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
