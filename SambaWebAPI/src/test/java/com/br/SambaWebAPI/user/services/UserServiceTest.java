package com.br.SambaWebAPI.user.services;

import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.factory.UserCreationFactory;
import com.br.SambaWebAPI.user.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceTest {


    @Mock
    private ProcessBuilder processBuilderAdapter;

    @Mock
    private Process process;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() throws InterruptedException {
        when(processBuilderAdapter.command(anyString())).thenReturn(processBuilderAdapter);
        when(processBuilderAdapter.redirectInput((ProcessBuilder.Redirect) any())).thenReturn(processBuilderAdapter);
        when(process.waitFor()).thenReturn(0);
    }

    @Test
    @DisplayName("""
         Dado o desejo do usuario criar o usuario
        quando o usuário realizar a criação de usuário
        então deve realizar a criação de usuario com sucesso
        """)
    void createUser() throws Exception {
        User user = new User("usuario");
        SudoAuthentication sudoAuthentication = new SudoAuthentication("senha");
        boolean result = userService.createUser(user, sudoAuthentication);

        assertTrue(result);
    }

    @Test
    @DisplayName("""
         Dado o desejo do usuario criar o usuario
        quando o usuário realizar a criação de usuário
        então deve retronar erro código 1
        """)
    public void testCreateUserFailureExitCode() throws Exception {
        User user = new User("usuario");
        SudoAuthentication sudoAuthentication = new SudoAuthentication("senha");
        when(process.waitFor()).thenReturn(1);

        try {
            userService.createUser(user, sudoAuthentication);
            fail("Deveria ter lançado uma exceção");
        } catch (Exception e) {
            assertEquals(UserCreationFactory.createException(1), e);
        }
    }

    @Test
    @DisplayName("""
     Dado o desejo do usuario criar o usuario
    quando o usuário realizar a criação de usuário duplicado
    então deve retronar erro código 9
    """)
    public void testCreateUserFailureExitCodeUserAlreadyExists() throws Exception {
        User user = new User("usuario");
        SudoAuthentication sudoAuthentication = new SudoAuthentication("senha");
        when(process.waitFor()).thenReturn(9);

        try {
            userService.createUser(user, sudoAuthentication);
            fail("Deveria ter lançado uma exceção");
        } catch (Exception e) {
            assertEquals(UserCreationFactory.createException(9), e);
            assertEquals("Não foi possível criar a pasta home", e.getMessage());
        }

        assertFalse(userService.getUser(user));
    }

    @Test
    @DisplayName("""
         Dado o desejo do usuario criar o usuario
        quando o usuário realizar a criação de usuário
        então deve retornar exçeão de erro durante a execução do processo
        """)
    public void testCreateUserFailureProcessException() throws Exception {
        User user = new User("usuario");
        SudoAuthentication sudoAuthentication = new SudoAuthentication("senha");
        when(process.waitFor()).thenThrow(new IOException("Erro durante a execução do processo"));

        try {
            userService.createUser(user, sudoAuthentication);
            fail("Deveria ter lançado uma exceção");
        } catch (Exception e) {
            assertEquals(IOException.class, e.getClass());
        }
    }

    @Test
    @DisplayName("""
         Dado o desejo do usuario criar o usuario
        quando o usuário realizar a criação de usuário
        então deve retronar erro de NullPointeException na senha sudo
        """)
    public void testCreateUserFailureSudoAuthenticationNull() throws Exception {
        User user = new User("usuario");

        try {
            userService.createUser(user, null);
            fail("Deveria ter lançado uma exceção");
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }
    }

    @Test
    @DisplayName("""
         Dado o desejo do usuario criar o usuario
        quando o usuário realizar a criação de usuário
        então deve retronar erro de NullPointeException no usuario
        """)
    public void testCreateUserFailureUserNull() throws Exception {
        SudoAuthentication sudoAuthentication = new SudoAuthentication("senha");

        try {
            userService.createUser(null, sudoAuthentication);
            fail("Deveria ter lançado uma exceção");
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }
    }

    @Test
    void removeUser() {
    }

    @Test
    void getUser() {
    }

    @Test
    void createSambaUser() {
    }

    @Test
    void removeSambaUser() {
    }
}