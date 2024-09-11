package com.br.SambaWebAPI.user.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.enums.CreateUserErrorCode;
import com.br.SambaWebAPI.user.enums.DeleteUserErrorCode;
import com.br.SambaWebAPI.user.exceptions.CreateUserException;
import com.br.SambaWebAPI.user.exceptions.DeleteUserException;
import com.br.SambaWebAPI.user.exceptions.CreateUserSambaException;
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

import java.io.ByteArrayInputStream;
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
        user.setPassword("user_password");
    }

    @Test
    @DisplayName("""
        Dado um processo de criação de usuário,
        quando o usuário é criado com sucesso,
        então deve retornar true""")
    public void createUserSuccess() throws Exception {
        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");

        String[] commandArgs = new String[] {
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
    public void createUserFailWithDifferentErrorCodes() throws Exception {
        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);
        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(Mockito.any(String[].class))).thenReturn(processBuilder);

        UserService userService = new UserService(processBuilderAdapter);

        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");

        String[] commandArgs = new String[] {
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

        CreateUserErrorCode[] errorCodes = new CreateUserErrorCode[] {
                CreateUserErrorCode.CANT_UPDT_PASSWD_FILE,
                CreateUserErrorCode.USR_ALREADY_EXISTS,
                CreateUserErrorCode.CANT_CREATE_HOME_DIR,
                CreateUserErrorCode.CANT_CREATE_MAIL_SPOOL,
                CreateUserErrorCode.CANT_UPDATE_SELINUX,
        };

        for (int i = 0; i < exitCodes.length; i++) {
            when(process.waitFor()).thenReturn(exitCodes[i]);
            try {
                userService.createUser(user, sudoAuthentication);
            } catch (CreateUserException e) {
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
        } catch (CreateUserException e) {
            Assertions.assertEquals(CreateUserErrorCode.GENERIC_ERROR, e.getErrorCode());
        }
        verify(processBuilderAdapter, times(exitCodes.length + 1)).command(Mockito.eq(commandArgs));
        verify(processBuilder, times(exitCodes.length + 1)).start();
        verify(process, times(exitCodes.length + 1)).waitFor();
    }

    @Test
    @DisplayName("""
    Dado um processo de ler a lista de usuário,
    quando é lida com sucesso,
    então deve retornar true""")
    public void getUserSuccess() throws Exception {
        user.setUser("sambauser");
        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("sambauser");

        String[] commandArgs = new String[] {
                CommandConstants.BASH,
                CommandConstants.EXECUTE_COMMAND,
                CommandConstants.GET_USER + user.getUser()
        };

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);
        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);
        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));
        when(process.getInputStream()).thenReturn(new ByteArrayInputStream("some output".getBytes()));

        UserService userService = new UserService(processBuilderAdapter);

        boolean result = userService.getUser(user);
        System.out.println(user.getUser());

        assertTrue(result);

        verify(processBuilderAdapter).command(commandArgs);
        verify(processBuilder).start();
        verify(process).waitFor();
    }

    @Test
    @DisplayName("""
            Dado um processo de criação de usuário samba,
            quando criar usuário,
            então deve retornar true
            """)
    void createSambaUser() throws Exception, CreateUserSambaException {

        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");
        when(user.getPassword()).thenReturn("usuario_senha");

        String[] commandArgs = new String[] {
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.USER_SMB,
                CommandConstants.USER_ADD_SMB,
                user.getUser()
        };

        String[] commandCreateUserArgs = new String[] {
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.USER_ADD,
                user.getUser()
        };


        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);

        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        when(processBuilderAdapter.command(commandCreateUserArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);

        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));

        UserService userService = new UserService(processBuilderAdapter);

        boolean result = userService.createSambaUser(user, sudoAuthentication);

        assertTrue(result);

        verify(processBuilderAdapter, times(1)).command(commandArgs);
        verify(processBuilderAdapter, times(1)).command(commandCreateUserArgs);
        verify(processBuilder, times(2)).start();
        verify(process, times(3)).waitFor();
    }

    @Test
    @DisplayName("""
            Dado um processo de remoção de usuário samba,
            quando criar usuário,
            então deve retornar true
            """)
    void removeSambaUser() throws Exception, UserSambaDeleteException {

        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");
        when(user.getPassword()).thenReturn("usuario_senha");

        String[] commandArgs = new String[] {
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.USER_SMB,
                CommandConstants.USER_DEL_SMB,
                user.getUser()
        };

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);

        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);

        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));

        UserService userService = new UserService(processBuilderAdapter);

        boolean result = userService.removeSambaUser(user, sudoAuthentication);

        assertTrue(result);

        verify(processBuilderAdapter, times(1)).command(commandArgs);
        verify(processBuilder, times(1)).start();
        verify(process, times(2)).waitFor();
    }

@Test
@DisplayName("""
        Dado um processo de remoção de usuário,
        quando o usuário é removido com sucesso,
        então deve retornar true""")
public void removeUserSuccess() throws Exception {
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
public void removeUserFailWithDifferentErrorCodes() throws Exception {
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

    DeleteUserErrorCode[] errorCodes = new DeleteUserErrorCode[] {
            DeleteUserErrorCode.CANT_UPDT_PASSWD_FILE,
            DeleteUserErrorCode.USER_DOESNT_EXIST,
            DeleteUserErrorCode.USER_LOGGED,
            DeleteUserErrorCode.CANT_UPDT_GROUP_FILE,
            DeleteUserErrorCode.CANT_REMOVE_HOME_DIR
    };

    for (int i = 0; i < exitCodes.length; i++) {
        when(process.waitFor()).thenReturn(exitCodes[i]);
        try {
            userService.removeUser(user, sudoAuthentication);
            Assertions.fail("Deveria ter lançado uma exceção");
        } catch (DeleteUserException e) {
            Assertions.assertEquals(errorCodes[i], e.getErrorCode());
        }
    }

    when(process.waitFor()).thenReturn(999);
    try {
        userService.removeUser(user, sudoAuthentication);
        Assertions.fail("Deveria ter lançado uma exceção");
    } catch (DeleteUserException e) {
        Assertions.assertEquals(DeleteUserErrorCode.GENERIC_ERROR, e.getErrorCode());
    }

    verify(processBuilderAdapter, times(exitCodes.length + 1)).command(commandArgs);
    verify(processBuilder, times(exitCodes.length + 1)).start();
    verify(process, times((exitCodes.length + 1) * 2)).waitFor();
}
}