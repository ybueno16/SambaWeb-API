package com.br.SambaWebAPI.user.controllers;

import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.password.services.PasswordService;
import com.br.SambaWebAPI.user.exceptions.CreateUserSambaException;
import com.br.SambaWebAPI.user.exceptions.UserSambaDeleteException;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.user.models.dto.UserOperationsDTO;
import com.br.SambaWebAPI.user.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private User user;

    @Mock
    private SudoAuthentication sudoAuthentication;

    @Mock
    ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    UserService userService = Mockito.mock(UserService.class);

    @Mock
    PasswordService passwordService = Mockito.mock(PasswordService.class);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        user.setUser("user_name");
        user.setPassword("user_password");
        sudoAuthentication.setSudoPassword("sudo_password");
    }

    @Test
    @DisplayName("""
            Given a user creation process,
            when the user is created successfully,
            then it should return HTTP 200
         """)
    public void testUserCreation() throws Exception {
        UserController userController = new UserController(userService, passwordService);
        UserOperationsDTO userOperationsDTO = new UserOperationsDTO();
        userOperationsDTO.setUser(user);
        userOperationsDTO.setSudoAuthentication(sudoAuthentication);
        when(userService.createUser(user,sudoAuthentication)).thenReturn(true);
        ResponseEntity<?> response = userController.UserCreation(userOperationsDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("""
                Given a user listing process,
                when the user is created successfully,
                then it should return HTTP 200
                """)
    public void getUserCreation() throws Exception {

        UserController userController = new UserController(userService, passwordService);

        when(userService.getUser(user)).thenReturn(true);

        ResponseEntity<?> response = userController.getUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("""
            Given a user removal process,
            when the user is created successfully,
            then it should return HTTP 200
            """)
    public void removeUserCreation() throws Exception {
        UserController userController = new UserController(userService, passwordService);
        UserOperationsDTO userOperationsDTO = new UserOperationsDTO();
        userOperationsDTO.setUser(user);
        userOperationsDTO.setSudoAuthentication(sudoAuthentication);
        when(userService.removeUser(user,sudoAuthentication)).thenReturn(true);
        ResponseEntity<?> response = userController.removeUser(userOperationsDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("""
            Given a samba user creation process,
            when the user is created successfully,
            then it should return HTTP 200
            """)
    public void createSambaUserCreation() throws Exception, CreateUserSambaException {
        UserController userController = new UserController(userService, passwordService);
        UserOperationsDTO userOperationsDTO = new UserOperationsDTO();
        userOperationsDTO.setUser(user);
        userOperationsDTO.setSudoAuthentication(sudoAuthentication);
        when(userService.createSambaUser(user,sudoAuthentication)).thenReturn(true);
        ResponseEntity<?> response = userController.createSambaUser(userOperationsDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("""
            Given a samba user removal process,
            when the user is created successfully,
            then it should return HTTP 200
        """)
    public void deleteSambaUserCreation() throws Exception, UserSambaDeleteException {
        UserController userController = new UserController(userService, passwordService);
        UserOperationsDTO userOperationsDTO = new UserOperationsDTO();
        userOperationsDTO.setUser(user);
        userOperationsDTO.setSudoAuthentication(sudoAuthentication);
        when(userService.removeSambaUser(user,sudoAuthentication)).thenReturn(true);
        ResponseEntity<?> response = userController.removeSambaUser(userOperationsDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}