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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(classes = SambaWebApiApplication.class)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Value("${sudo.password}")
    private String sudoPassword;

    @BeforeEach
    public void tearDown() throws Exception {
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
    void createUser() throws Exception {
        User user = new User();
        SudoAuthentication sudoAuthentication = new SudoAuthentication();
        sudoAuthentication.setSudoPassword(sudoPassword);
        user.setUser("sambauser");
        boolean sucess = userService.createUser(user, sudoAuthentication);
        assertTrue(sucess);
    }

    @Test
    @DisplayName("""
            Dado o desejo do usuario criar o usuario
            quando o usuario digitar a senha errado
            então deve retornar uma exceção
            """)
    void createUserWithErrorCantUpdtPasswdFile() {
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
        sudoAuthentication.setSudoPassword(sudoPassword);
        user.setUser("sambauser");
        userService.createUser(user, sudoAuthentication);
        try {
            userService.createUser(user, sudoAuthentication);
            fail("Deveria ter lançado uma exceção UserCreationException");
        } catch (UserCreationException e) {
            assertEquals(UserCreationErrorCode.USR_ALREADY_EXISTS, e.getErrorCode(), "Expected error code to be USR_ALREADY_EXISTS, but was " + e.getErrorCode().getErrorMessage());
        }
    }
}