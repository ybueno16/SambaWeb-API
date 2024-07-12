package com.br.SambaWebAPI.user.services;

import com.br.SambaWebAPI.SambaWebApiApplication;
import com.br.SambaWebAPI.adapter.impl.ProcessBuilderAdapterImpl;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.exceptions.UserCreationException;
import com.br.SambaWebAPI.user.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SambaWebApiApplication.class)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void createUser() throws Exception {
        User user = new User();
        SudoAuthentication sudoAuthentication = new SudoAuthentication();
        sudoAuthentication.setSudoPassword("senhaforte123");
        user.setUser("sambauser");
        user.setPassword("senhaforte123");
        boolean succes = userService.createUser(user,sudoAuthentication);
        assertTrue(succes);
    }
}