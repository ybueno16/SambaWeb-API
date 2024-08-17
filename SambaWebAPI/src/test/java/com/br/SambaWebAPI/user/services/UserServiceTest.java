package com.br.SambaWebAPI.user.services;


import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.utils.CommandConstants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private ProcessBuilderAdapter processBuilderAdapter;

    @Mock
    private Process process;

    @Mock
    private BufferedReader reader;

    private UserService userService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(processBuilderAdapter);
    }

    @Test
    public void testGetUserSuccess() throws Exception {
        // Configura o mock para retornar um código de saída 0
        when(process.waitFor()).thenReturn(0);

        // Chama o método getUser
        boolean result = userService.getUser(new User("username"));

        // Verifica se o resultado é true
        assertTrue(result);

        // Verifica se o processBuilder foi chamado com os parâmetros corretos
        verify(processBuilderAdapter).command(CommandConstants.BASH, CommandConstants.EXECUTE_COMMAND, CommandConstants.GET_USER + "username");
    }

}