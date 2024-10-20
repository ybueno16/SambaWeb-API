package com.br.SambaWebAPI.testIntegration.user.services;

import com.br.SambaWebAPI.SambaWebApiApplication;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.user.services.UserService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
//@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(classes = SambaWebApiApplication.class)
class UserServiceIntegrationTest {

    @Autowired
    UserService userService;

    String username = ("sambauser");
    String sudoPassword = ("senhaforte123");

    @BeforeEach
    public void tearDown() throws Exception {

        User user = new User();
        user.setUser(username);
        SudoAuthentication sudoAuthentication = new SudoAuthentication();
        sudoAuthentication.setSudoPassword(sudoPassword);

        if (userService.getUser(user)) {
            try {
                userService.removeUser(user, sudoAuthentication);
            }catch (Exception ignored){

            }
        }
    }


    @Test
    @DisplayName("""
        Given the user's desire to create the user
        when the user performs user creation
        then you must successfully create the user
        """)
    void createUser() throws Exception {
        User user = new User();
        user.setUser("testeintegracaousuario");
        SudoAuthentication sudoAuthentication = new SudoAuthentication();
        sudoAuthentication.setSudoPassword(sudoPassword);
        boolean sucess = userService.createUser(user, sudoAuthentication);
        assertTrue(sucess);
    }
}