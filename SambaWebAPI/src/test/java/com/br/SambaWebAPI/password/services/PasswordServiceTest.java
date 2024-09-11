package com.br.SambaWebAPI.password.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.password.enums.CreatePasswordErrorCode;
import com.br.SambaWebAPI.password.exceptions.CreatePasswordException;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.exceptions.UserSambaDeleteException;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.utils.CommandConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class PasswordServiceTest {

    @Mock
    private ProcessBuilderAdapter processBuilderAdapter;

    @Mock
    private ProcessBuilder processBuilder;

    @Mock
    private SudoAuthentication sudoAuthentication;

    @Mock
    private Process process;

    @Mock
    private User user;

    @InjectMocks
    private PasswordService passwordService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sudoAuthentication.setSudoPassword("sudo_password");
        user.setUser("user_name");
        user.setPassword("user_password");
    }

    @Test
    @DisplayName("""
            Dado um processo de criação de senha de um usuárui,
            quando criar a senha do usuário,
            então deve retornar true
            """)
    void removeSambaUser() throws Exception, UserSambaDeleteException {

        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");
        when(user.getPassword()).thenReturn("usuario_senha");

        String[] commandArgs = new String[] {
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.CH_PASSWD,
                user.getUser()
        };

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);

        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);

        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));

        PasswordService passwordService = new PasswordService(processBuilderAdapter);

        boolean result = passwordService.createPassword(user);

        assertTrue(result);

        verify(processBuilderAdapter, times(1)).command(commandArgs);
        verify(processBuilder, times(1)).start();
        verify(process, times(1)).waitFor();
    }
    @Test
    @DisplayName("""
            Dado um processo de remoção de usuário com diferentes códigos de saída,
            quando remover o usuário,
            então deve lançar exceção com código de erro correto""")
    public void removeUserFailWithDifferentErrorCodes() throws Exception {
        when(user.getUser()).thenReturn("user_name");
        when(user.getPassword()).thenReturn("password_user_name");


        String[] commandArgs = new String[] {
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.CH_PASSWD,
                user.getUser()
        };

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);
        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);
        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));

        PasswordService passwordService = new PasswordService(processBuilderAdapter);

        int[] exitCodes = new int[] {
                3,5
        };

        CreatePasswordErrorCode[] errorCodes = new CreatePasswordErrorCode[] {
                CreatePasswordErrorCode.PASSWD_FILE_MISSING,
                CreatePasswordErrorCode.PASSWD_FILE_BUSY,
        };

        for (int i = 0; i < exitCodes.length; i++) {
            when(process.waitFor()).thenReturn(exitCodes[i]);
            try {
                passwordService.createPassword(user);
                Assertions.fail("Deveria ter lançado uma exceção customizada");
            } catch (CreatePasswordException e) {
                Assertions.assertEquals(errorCodes[i], e.getErrorCode());
            }


        }

        when(process.waitFor()).thenReturn(999);
        try {
            passwordService.createPassword(user);
            Assertions.fail("Deveria ter lançado uma exceção");
        } catch (CreatePasswordException e) {
            Assertions.assertEquals(CreatePasswordErrorCode.GENERIC_ERROR, e.getErrorCode());
        }

        verify(processBuilderAdapter, times(exitCodes.length + 1)).command(commandArgs);
        verify(processBuilder, times(exitCodes.length + 1)).start();
        verify(process, times((exitCodes.length + 1))).waitFor();
    }
}