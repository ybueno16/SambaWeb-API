package com.br.SambaWebAPI.user.services;

import com.br.SambaWebAPI.SambaWebApiApplication;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.enums.UserCreationErrorCode;
import com.br.SambaWebAPI.user.exceptions.UserCreationException;
import com.br.SambaWebAPI.user.models.User;
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

        User user = new User(username);
        SudoAuthentication sudoAuthentication = new SudoAuthentication(sudoPassword);

        if (userService.getUser(user)) {
            try {
                userService.removeUser(user, sudoAuthentication);
            }catch (Exception ignored){

            }
        }
    }


    @Test
    @DisplayName("""
        Dado o desejo do usuario criar o usuario
        quando o usuário realizar a criação de usuário
        então deve realizar a criação de usuario com sucesso
        """)
    void createUser() throws Exception {
        User user = new User("testsambauser");
        SudoAuthentication sudoAuthentication = new SudoAuthentication(sudoPassword);
        boolean sucess = userService.createUser(user, sudoAuthentication);
        assertTrue(sucess);
    }

    @Test
    @DisplayName("""
        Dado o desejo do usuario criar o usuario
        quando já tiver algum usuário com o mesmo nome
        então deve retornar uma exceção
        """)
    void createUserWithErrorUsrAlreadyExists() throws Exception {
        User user = new User(username);
        SudoAuthentication sudoAuthentication = new SudoAuthentication(username);
        userService.createUser(user, sudoAuthentication);
        try {
            userService.createUser(user, sudoAuthentication);
            fail("Deveria ter lançado uma exceção UserCreationException");
        } catch (UserCreationException e) {
            assertEquals(UserCreationErrorCode.USR_ALREADY_EXISTS, e.getErrorCode(), "Expected error code to be USR_ALREADY_EXISTS, but was " + e.getErrorCode().getErrorMessage());
        }
    }
}