package com.br.SambaWebAPI.password.services;

import com.br.SambaWebAPI.SambaWebApiApplication;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.user.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SambaWebApiApplication.class)
class PasswordServiceTest {
String senha = "Isacreeper1";

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserService userService;

    private boolean userCreated = false;

    @BeforeEach
    public void tearDown() throws Exception {
        User user = new User();
        SudoAuthentication sudoAuthentication = new SudoAuthentication();
        sudoAuthentication.setSudoPassword(senha);
        user.setUser("sambauser");
        if (userService.getUser(user)) {
            userService.removeUser(user, sudoAuthentication);
        }
    }

    @Test
    void createPassword() throws Exception {
        User user = new User();
        SudoAuthentication sudoAuthentication = new SudoAuthentication();
        sudoAuthentication.setSudoPassword(senha);
        user.setUser("sambauser");
        sudoAuthentication.setSudoPassword(senha);
        userService.createUser(user,sudoAuthentication);
        user.setPassword("Isacreeper1");
        boolean sucess = passwordService.createPassword(user,sudoAuthentication);
        assertTrue(sucess);
        userCreated = true;
    }
}