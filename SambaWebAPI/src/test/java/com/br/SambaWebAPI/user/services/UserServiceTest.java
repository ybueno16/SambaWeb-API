package com.br.SambaWebAPI.user.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.enums.UserCreationErrorCode;
import com.br.SambaWebAPI.user.enums.UserDeleteErrorCode;
import com.br.SambaWebAPI.user.exceptions.UserCreationException;
import com.br.SambaWebAPI.user.exceptions.UserDeleteException;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


public class UserServiceTest {
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
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sudoAuthentication.setSudoPassword("sudo_password");
        user.setUser("user_name");
    }

    @Test
    @DisplayName("""
        Dado um processo de criação de usuário,
        quando o usuário é criado com sucesso,
        então deve retornar true""")
    public void CreateUserSuccess() throws Exception {
        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");

        String[] commandArgs = new String[] {
                CommandConstants.BASH,
                CommandConstants.EXECUTE_COMMAND,
                CommandConstants.ECHO,
                sudoAuthentication.getSudoPassword(),
                "\" | ",
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.USER_ADD,
                user.getUser()
        };

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);
        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);
        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));

        UserService userService = new UserService(processBuilderAdapter);

        boolean result = userService.createUser(user, sudoAuthentication);

        assertTrue(result);

        verify(processBuilderAdapter).command(commandArgs);
        verify(processBuilder).start();
        verify(process).waitFor();
    }

    @Test
    @DisplayName("""
            Dado um processo de criação de usuário com diferentes códigos de saída,
            quando criar usuário,
            então deve lançar exceção com código de erro correto""")
    public void CreateUserFailWithDifferentErrorCodes() throws Exception {
        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);
        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(Mockito.any(String[].class))).thenReturn(processBuilder);

        UserService userService = new UserService(processBuilderAdapter);

        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");

        String[] commandArgs = new String[] {
                CommandConstants.BASH,
                CommandConstants.EXECUTE_COMMAND,
                CommandConstants.ECHO,
                sudoAuthentication.getSudoPassword(),
                "\" | ",
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.USER_ADD,
                user.getUser()
        };

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);
        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));

        int[] exitCodes = new int[] {
                1, 9, 12, 13, 14
        };

        UserCreationErrorCode[] errorCodes = new UserCreationErrorCode[] {
                UserCreationErrorCode.CANT_UPDT_PASSWD_FILE,
                UserCreationErrorCode.USR_ALREADY_EXISTS,
                UserCreationErrorCode.CANT_CREATE_HOME_DIR,
                UserCreationErrorCode.CANT_CREATE_MAIL_SPOOL,
                UserCreationErrorCode.CANT_UPDATE_SELINUX,
        };

        for (int i = 0; i < exitCodes.length; i++) {
            when(process.waitFor()).thenReturn(exitCodes[i]);
            try {
                userService.createUser(user, sudoAuthentication);
            } catch (UserCreationException e) {
                Assertions.assertEquals(errorCodes[i], e.getErrorCode());
            }
            verify(processBuilderAdapter, times(i + 1)).command(Mockito.eq(commandArgs));
            verify(processBuilder, times(i + 1)).start();
            verify(process, times(i + 1)).waitFor();
        }

        when(process.waitFor()).thenReturn(999);
        try {
            userService.createUser(user, sudoAuthentication);
            Assertions.fail("Deveria ter lançado uma exceção");
        } catch (UserCreationException e) {
            Assertions.assertEquals(UserCreationErrorCode.GENERIC_ERROR, e.getErrorCode());
        }
        verify(processBuilderAdapter, times(exitCodes.length + 1)).command(Mockito.eq(commandArgs));
        verify(processBuilder, times(exitCodes.length + 1)).start();
        verify(process, times(exitCodes.length + 1)).waitFor();
    }

    @Test
    @DisplayName("""
        Dado um processo de remoção de usuário,
        quando o usuário é removido com sucesso,
        então deve retornar true""")
    public void RemoveUserSuccess() throws Exception {
        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");

        String[] commandArgs = new String[] {
                CommandConstants.ECHO,
                sudoAuthentication.getSudoPassword(),
                "\" | ",
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.USER_DEL, user.getUser()
        };

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);
        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);
        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));

        UserService userService = new UserService(processBuilderAdapter);

        boolean result = userService.removeUser(user, sudoAuthentication);

        assertTrue(result);

        verify(processBuilderAdapter).command(commandArgs);
        verify(processBuilder).start();
        verify(process,times(2)).waitFor();
    }

    @Test
    @DisplayName("""
            Dado um processo de remoção de usuário com diferentes códigos de saída,
            quando remover o usuário,
            então deve lançar exceção com código de erro correto""")
    public void RemoveUserFailWithDifferentErrorCodes() throws Exception {
        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");

        String[] commandArgs = new String[] {
                CommandConstants.ECHO,
                sudoAuthentication.getSudoPassword(),
                "\" | ",
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.USER_DEL, user.getUser()
        };

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);
        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);
        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));

        UserService userService = new UserService(processBuilderAdapter);

        int[] exitCodes = new int[] {
                1, 6, 8, 10, 12
        };

        UserDeleteErrorCode[] errorCodes = new UserDeleteErrorCode[] {
                UserDeleteErrorCode.CANT_UPDT_PASSWD_FILE,
                UserDeleteErrorCode.USER_DOESNT_EXIST,
                UserDeleteErrorCode.USER_LOGGED,
                UserDeleteErrorCode.CANT_UPDT_GROUP_FILE,
                UserDeleteErrorCode.CANT_REMOVE_HOME_DIR
        };

        for (int i = 0; i < exitCodes.length; i++) {
            when(process.waitFor()).thenReturn(exitCodes[i]);
            try {
                userService.removeUser(user, sudoAuthentication);
                Assertions.fail("Deveria ter lançado uma exceção");
            } catch (UserDeleteException e) {
                Assertions.assertEquals(errorCodes[i], e.getErrorCode());
            }
        }

        when(process.waitFor()).thenReturn(999);
        try {
            userService.removeUser(user, sudoAuthentication);
            Assertions.fail("Deveria ter lançado uma exceção");
        } catch (UserDeleteException e) {
            Assertions.assertEquals(UserDeleteErrorCode.GENERIC_ERROR, e.getErrorCode());
        }

        verify(processBuilderAdapter, times(exitCodes.length + 1)).command(commandArgs);
        verify(processBuilder, times(exitCodes.length + 1)).start();
        verify(process, times((exitCodes.length + 1) * 2)).waitFor();
    }


}
