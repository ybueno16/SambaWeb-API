package com.br.SambaWebAPI.login.controllers;

import static com.br.SambaWebAPI.config.Global.API_URL_SAMBA;

import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.config.swagger.DefaultOperation;
import com.br.SambaWebAPI.login.exceptions.LoginException;
import com.br.SambaWebAPI.login.services.LoginService;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
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
@RequestMapping(API_URL_SAMBA + "/login")
public class LoginController {
    private final ObjectMapper objectMapper;
    private final LoginService loginService;

    @Autowired
    public LoginController(ObjectMapper objectMapper, LoginService loginService) {
        this.objectMapper = objectMapper;
        this.loginService = loginService;
    }

    @PostMapping
    @DefaultOperation(
            summary = "Login",
            description = "Login",
            tags = {"Login"})
    public ResponseEntity<?> login(@RequestBody Map<String, Object> json) {
        User user = objectMapper.convertValue(json.get("user"), User.class);
        SudoAuthentication sudoAuthentication =
                objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
        try {
            loginService.login(user,sudoAuthentication);
            return DefaultResponseEntityFactory.create("Login successful.", user, HttpStatus.OK);
        } catch (LoginException e) {
            return DefaultResponseEntityFactory.create(
                    e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            return DefaultResponseEntityFactory.create(
                    "Generic error. An unknown error occurred during login.",
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
