package com.br.SambaWebAPI.user.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.factory.UserCreationFactory;
import com.br.SambaWebAPI.user.models.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {


    @Mock
    private ProcessBuilder processBuilderAdapter;

    @Mock
    private Process process;

    @InjectMocks
    private UserService userService;

    @Before
    public void setup() throws InterruptedException {
        when(processBuilderAdapter.command(anyString())).thenReturn(processBuilderAdapter);
        when(processBuilderAdapter.redirectInput((ProcessBuilder.Redirect) any())).thenReturn(processBuilderAdapter);
        when(process.waitFor()).thenReturn(0); // código de saída 0 para o caso de sucesso
    }

    @Test
    void createUser() throws Exception {
        // Arrange
        User user = new User("usuario");
        SudoAuthentication sudoAuthentication = new SudoAuthentication("senha");

        // Act
        boolean result = userService.createUser(user, sudoAuthentication);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testCreateUserFailureExitCode() throws Exception {
        // Arrange
        User user = new User("usuario");
        SudoAuthentication sudoAuthentication = new SudoAuthentication("senha");
        when(process.waitFor()).thenReturn(1); // código de saída!= 0

        // Act e Assert
        try {
            userService.createUser(user, sudoAuthentication);
            fail("Deveria ter lançado uma exceção");
        } catch (Exception e) {
            assertEquals(UserCreationFactory.createException(1), e);
        }
    }

    @Test
    public void testCreateUserFailureProcessException() throws Exception {
        // Arrange
        User user = new User("usuario");
        SudoAuthentication sudoAuthentication = new SudoAuthentication("senha");
        when(process.waitFor()).thenThrow(new IOException("Erro durante a execução do processo"));

        // Act e Assert
        try {
            userService.createUser(user, sudoAuthentication);
            fail("Deveria ter lançado uma exceção");
        } catch (Exception e) {
            assertEquals(IOException.class, e.getClass());
        }
    }

    @Test
    public void testCreateUserFailureSudoAuthenticationNull() throws Exception {
        // Arrange
        User user = new User("usuario");

        // Act e Assert
        try {
            userService.createUser(user, null);
            fail("Deveria ter lançado uma exceção");
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }
    }

    @Test
    public void testCreateUserFailureUserNull() throws Exception {
        // Arrange
        User user = null;
        SudoAuthentication sudoAuthentication = new SudoAuthentication("senha");

        // Act e Assert
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