package com.br.SambaWebAPI.password.services;

import com.br.SambaWebAPI.SambaWebApiApplication;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(classes = SambaWebApiApplication.class)
class PasswordServiceTest {

    @Autowired
    private PasswordService passwordService;
    @Autowired
    private UserService userService;

    @Value("${sudo.password}")
    private String sudoPassword;

    @BeforeEach
    public void setup() throws Exception {
        User user = new User();
        SudoAuthentication sudoAuthentication = new SudoAuthentication();
        sudoAuthentication.setSudoPassword(sudoPassword);
        user.setUser("sambauser");
        if (userService.getUser(user)) {
            try {
                userService.removeUser(user, sudoAuthentication);
            }catch (Exception ignored){

            }
        }
    }

    @Test
    void createPassword() throws Exception {
        User user = new User();
        SudoAuthentication sudoAuthentication = new SudoAuthentication();
        sudoAuthentication.setSudoPassword(sudoPassword);
        user.setUser("sambauser");
        sudoAuthentication.setSudoPassword(sudoPassword);
        userService.createUser(user,sudoAuthentication);
        user.setPassword("senhaforte1234");
        boolean sucess = passwordService.createPassword(user,sudoAuthentication);
        assertTrue(sucess);
    }
}