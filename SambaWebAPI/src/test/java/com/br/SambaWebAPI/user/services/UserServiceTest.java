package com.br.SambaWebAPI.user.services;

import com.br.SambaWebAPI.SambaWebApiApplication;
import com.br.SambaWebAPI.adapter.impl.ProcessBuilderAdapterImpl;
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
@SpringBootTest(classes = SambaWebApiApplication.class)
class UserServiceTest {

    @Autowired
    UserService userService;

    @BeforeEach
    public void tearDown() throws Exception {
        User user = new User();
        SudoAuthentication sudoAuthentication = new SudoAuthentication();
        sudoAuthentication.setSudoPassword("Isacreeper1");
        user.setUser("sambauser");
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
            quando o usuario digitar a senha correta
            então deve retornar um sucesso
            """)
    void createUser() throws Exception {
        User user = new User();
        SudoAuthentication sudoAuthentication = new SudoAuthentication();
        sudoAuthentication.setSudoPassword("Isacreeper1");
        user.setUser("sambauser");
        assertTrue(userService.createUser(user, sudoAuthentication));
    }

    @Test
    @DisplayName("""
            Dado o desejo do usuario criar o usuario
            quando o usuario digitar a senha errado
            então deve retornar uma exceção
            """)
    void createUserWithErrorCantUpdtPasswdFIle() {
        User user = new User();
        SudoAuthentication sudoAuthentication = new SudoAuthentication();
        sudoAuthentication.setSudoPassword("fafasfsa");
        user.setUser("sambauser");

        UserCreationException exception = assertThrows(UserCreationException.class, () -> userService.createUser(user, sudoAuthentication));

        assertEquals(UserCreationErrorCode.CANT_UPDT_PASSWD_FILE, exception.getErrorCode());
    }

    @Test
    @DisplayName("""
        Dado o desejo do usuario criar o usuario
        quando já tiver algum usuário com o mesmo nome
        então deve retornar uma exceção
        """)
    void createUserWithErrorUsrAlreadyExists() throws Exception {
        User user = new User();
        SudoAuthentication sudoAuthentication = new SudoAuthentication();
        sudoAuthentication.setSudoPassword("Isacreeper1");
        user.setUser("sambauser");
        userService.createUser(user, sudoAuthentication);
        try {
            userService.createUser(user, sudoAuthentication);
            fail("Deveria ter lançado uma exceção UserCreationException");
        } catch (UserCreationException e) {
            assertEquals(UserCreationErrorCode.USR_ALREADY_EXISTS, e.getErrorCode());
        }
    }



}