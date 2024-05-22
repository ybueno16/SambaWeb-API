package com.br.SambaWebAPI.user.services;

import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.enums.UserCreationErrorCode;
import com.br.SambaWebAPI.user.exceptions.UserCreationException;
import com.br.SambaWebAPI.user.factory.UserCreationFactory;
import com.br.SambaWebAPI.user.models.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {


    @Test
    public void testCreateUserSuccess() throws InterruptedException, IOException, UserCreationException, NoSuchFieldException, IllegalAccessException, UserCreationFactory {
        User user = new User();
        user.setUser("testuser");
        user.setPassword("testpassword");
        user.setGroupList(null);

        SudoAuthentication sudoAuthentication = new SudoAuthentication();

        Field sudoPasswordField = sudoAuthentication.getClass().getDeclaredField("sudoPassword");
        sudoPasswordField.setAccessible(true);
        sudoPasswordField.set(sudoAuthentication, "Isacreeper1");

        UserService userService = new UserService();

        boolean result = userService.createUser(user, sudoAuthentication);
        assertTrue(result);
    }


}