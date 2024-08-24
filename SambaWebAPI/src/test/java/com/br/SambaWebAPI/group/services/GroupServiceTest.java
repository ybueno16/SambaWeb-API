package com.br.SambaWebAPI.group.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.group.enums.AddUserToGroupErrorCode;
import com.br.SambaWebAPI.group.enums.GroupCreationErrorCode;
import com.br.SambaWebAPI.group.enums.GroupDeleteErrorCode;
import com.br.SambaWebAPI.group.exceptions.AddUserToGroupException;
import com.br.SambaWebAPI.group.exceptions.GroupCreationException;
import com.br.SambaWebAPI.group.exceptions.GroupDeleteException;
import com.br.SambaWebAPI.group.models.Group;
import com.br.SambaWebAPI.password.enums.PasswordCreationErrorCode;
import com.br.SambaWebAPI.password.exceptions.PasswordCreationException;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.password.services.PasswordService;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class GroupServiceTest {

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

    @Mock
    private Group group;


    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sudoAuthentication.setSudoPassword("sudo_password");
        user.setUser("user_name");
        group.setName("group_name");

    }

    @Test
    @DisplayName("""
            Dado um processo de criação de grupo,
            quando criar o grupo com sucesso,
            então deve retornar true
            """)
    void createGroup() throws Exception {
        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");
        when(user.getUser()).thenReturn("group_name");


        String[] commandArgs = new String[] {
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.GROUP_ADD,
                group.getName()
        };

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);

        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);

        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));

        GroupService groupService = new GroupService(processBuilderAdapter);

        boolean result = groupService.createGroup(group,sudoAuthentication);

        assertTrue(result);

        verify(processBuilderAdapter, times(1)).command(commandArgs);
        verify(processBuilder, times(1)).start();
        verify(process, times(2)).waitFor();
    }


    @Test
    @DisplayName("""
            Dado um processo de criação de grupo com diferentes códigos de saída,
            quando criar o grupo,
            então deve lançar exceção com código de erro correto""")
    public void createGRoupFailWithDifferentErrorCodes() throws Exception {
        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");
        when(user.getUser()).thenReturn("group_name");


        String[] commandArgs = new String[] {
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.GROUP_ADD,
                group.getName()
        };

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);
        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);
        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));

        GroupService groupService = new GroupService(processBuilderAdapter);

        int[] exitCodes = new int[] {
                4,9,10
        };

        GroupCreationErrorCode[] errorCodes = new GroupCreationErrorCode[] {
                GroupCreationErrorCode.GID_ALREADY_EXISTS,
                GroupCreationErrorCode.GROUP_NAME_NOT_UNIQUE,
                GroupCreationErrorCode.CANT_UPDT_GROUP_FILE,
        };

        for (int i = 0; i < exitCodes.length; i++) {
            when(process.waitFor()).thenReturn(exitCodes[i]);
            try {
                groupService.createGroup(group,sudoAuthentication);
                Assertions.fail("Deveria ter lançado uma exceção customizada");
            } catch (GroupCreationException e) {
                Assertions.assertEquals(errorCodes[i], e.getErrorCode());
            }


        }

        when(process.waitFor()).thenReturn(999);
        try {
            groupService.createGroup(group,sudoAuthentication);
            Assertions.fail("Deveria ter lançado uma exceção");
        } catch (GroupCreationException e) {
            Assertions.assertEquals(GroupCreationErrorCode.GENERIC_ERROR, e.getErrorCode());
        }

        verify(processBuilderAdapter, times(exitCodes.length + 1)).command(commandArgs);
        verify(processBuilder, times(exitCodes.length + 1)).start();
        verify(process, times((exitCodes.length + 1) * 2)).waitFor();
    }

    @Test
    @DisplayName("""
            Dado um processo de adição do usuário a um grupo,
            quando adicionar o usuario ao grupo com sucesso,
            então deve retornar true
            """)
    void addUserToGroup() throws Exception {
        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");
        when(user.getUser()).thenReturn("group_name");


        String[] commandArgs = new String[] {
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.USER_MOD,
                CommandConstants.ADD_GROUP_OPTION,
                group.getName(),
                user.getUser()
        };

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);

        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);

        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));

        GroupService groupService = new GroupService(processBuilderAdapter);

        boolean result = groupService.addUserToGroup(group,user,sudoAuthentication);

        assertTrue(result);

        verify(processBuilderAdapter, times(1)).command(commandArgs);
        verify(processBuilder, times(1)).start();
        verify(process, times(1)).waitFor();
    }

    @Test
    @DisplayName("""
            Dado um processo de adição de usuario ao grupo com diferentes códigos de saída,
            quando adicionar o usuario ao grupo,
            então deve lançar exceção com código de erro correto""")
    public void addUsertoGoupFailWithDifferentErrorCodes() throws Exception {
        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");
        when(user.getUser()).thenReturn("group_name");


        String[] commandArgs = new String[] {
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.USER_MOD,
                CommandConstants.ADD_GROUP_OPTION,
                group.getName(),
                user.getUser()
        };

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);
        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);
        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));

        GroupService groupService = new GroupService(processBuilderAdapter);

        int[] exitCodes = new int[] {
                4,6,8,9,10,11,12
        };

        AddUserToGroupErrorCode[] errorCodes = new AddUserToGroupErrorCode[] {
                AddUserToGroupErrorCode.UID_ALREADY_EXISTS,
                AddUserToGroupErrorCode.LOGIN_DOES_NOT_EXIST,
                AddUserToGroupErrorCode.LOGIN_IN_USE,
                AddUserToGroupErrorCode.NEW_LOGNAME_ALREADY_EXISTS,
                AddUserToGroupErrorCode.CANT_UPDATE_GROUP_DB,
                AddUserToGroupErrorCode.INSUFFICIENT_SPACE,
                AddUserToGroupErrorCode.CANT_MOVE_HOME_DIR,
        };

        for (int i = 0; i < exitCodes.length; i++) {
            when(process.waitFor()).thenReturn(exitCodes[i]);
            try {
                groupService.addUserToGroup(group,user,sudoAuthentication);
                Assertions.fail("Deveria ter lançado uma exceção customizada");
            } catch (AddUserToGroupException e) {
                Assertions.assertEquals(errorCodes[i], e.getErrorCode());
            }
        }

        when(process.waitFor()).thenReturn(999);
        try {
            groupService.addUserToGroup(group,user,sudoAuthentication);
            Assertions.fail("Deveria ter lançado uma exceção");
        } catch (AddUserToGroupException e) {
            Assertions.assertEquals(AddUserToGroupErrorCode.GENERIC_ERROR, e.getErrorCode());
        }

        verify(processBuilderAdapter, times(exitCodes.length + 1)).command(commandArgs);
        verify(processBuilder, times(exitCodes.length + 1)).start();
        verify(process, times((exitCodes.length + 1))).waitFor();
    }

    @Test
    @DisplayName("""
            Dado um processo de remoção do grupo,
            quando remover o grupo com sucesso,
            então deve retornar true
            """)
    void deleteUserToGroup() throws Exception {
        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");
        when(user.getUser()).thenReturn("group_name");


        String[] commandArgs = new String[] {
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.GROUP_DEL,
                group.getName()
        };

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);

        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);

        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));

        GroupService groupService = new GroupService(processBuilderAdapter);

        boolean result = groupService.deleteGroup(group,sudoAuthentication);

        assertTrue(result);

        verify(processBuilderAdapter, times(1)).command(commandArgs);
        verify(processBuilder, times(1)).start();
        verify(process, times(1)).waitFor();
    }

    @Test
    @DisplayName("""
            Dado um processo de adição de usuario ao grupo com diferentes códigos de saída,
            quando adicionar o usuario ao grupo,
            então deve lançar exceção com código de erro correto""")
    public void deleteGroupFailWithDifferentErrorCodes() throws Exception {
        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(user.getUser()).thenReturn("user_name");
        when(user.getUser()).thenReturn("group_name");


        String[] commandArgs = new String[] {
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.GROUP_DEL,
                group.getName()
        };

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);
        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        when(processBuilderAdapter.command(commandArgs)).thenReturn(processBuilder);

        Process process = Mockito.mock(Process.class);
        when(processBuilder.start()).thenReturn(process);
        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));

        GroupService groupService = new GroupService(processBuilderAdapter);

        int[] exitCodes = new int[] {
                6,8,10
        };

        GroupDeleteErrorCode[] errorCodes = new GroupDeleteErrorCode[] {
                GroupDeleteErrorCode.GROUP_DOESNT_EXIST,
                GroupDeleteErrorCode.CANT_REMOVE_PRIMARY_GROUP,
                GroupDeleteErrorCode.CANT_UPDT_GROUP_FILE,
        };

        for (int i = 0; i < exitCodes.length; i++) {
            when(process.waitFor()).thenReturn(exitCodes[i]);
            try {
                groupService.deleteGroup(group,sudoAuthentication);
                Assertions.fail("Deveria ter lançado uma exceção customizada");
            } catch (GroupDeleteException e) {
                Assertions.assertEquals(errorCodes[i], e.getErrorCode());
            }
        }

        when(process.waitFor()).thenReturn(999);
        try {
            groupService.deleteGroup(group,sudoAuthentication);
            Assertions.fail("Deveria ter lançado uma exceção");
        } catch (GroupDeleteException e) {
            Assertions.assertEquals(GroupDeleteErrorCode.GENERIC_ERROR, e.getErrorCode());
        }

        verify(processBuilderAdapter, times(exitCodes.length + 1)).command(commandArgs);
        verify(processBuilder, times(exitCodes.length + 1)).start();
        verify(process, times((exitCodes.length + 1))).waitFor();
    }
}