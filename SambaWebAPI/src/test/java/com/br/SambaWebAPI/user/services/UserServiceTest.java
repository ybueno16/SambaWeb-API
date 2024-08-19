package com.br.SambaWebAPI.user.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.utils.CommandConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class UserServiceTest {
    private ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);;

    private ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);;

    @Mock
    private SudoAuthentication sudoAuthentication;

    @Mock
    private Process process;

    @Mock
    private User user;

    private UserService userService = new UserService(processBuilderAdapter);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sudoAuthentication.setSudoPassword("sudo_password");
        user.setUser("teste");
    }

    @Test
    public void RemoveUserSuccess() throws Exception {

        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");

        String[] commandArgs = new String[] {CommandConstants.ECHO, "sudo_password", "\" | ", CommandConstants.SUDO, CommandConstants.SUDO_STDIN, CommandConstants.USER_DEL, "user_name"};

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);
        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);
        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));
        when(process.waitFor()).thenReturn(0);



        UserService userService = new UserService(processBuilderAdapter);

        boolean result = userService.removeUser(user, sudoAuthentication);

        assertTrue(result);

        verify(processBuilderAdapter).command(commandArgs);
        verify(processBuilder).start();
        verify(process,times(2)).waitFor();
    }
}
