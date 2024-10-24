package com.br.SambaWebAPI.group.controllers;

import com.br.SambaWebAPI.group.exceptions.CreateGroupException;
import com.br.SambaWebAPI.group.models.Group;
import com.br.SambaWebAPI.group.models.dto.AssignUserToGroupDTO;
import com.br.SambaWebAPI.group.models.dto.GroupOperationDTO;
import com.br.SambaWebAPI.group.services.GroupService;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GroupControllerTest {

    @Mock
    private Group group;

    @Mock
    private User user;

    @Mock
    private SudoAuthentication sudoAuthentication;

    @Mock
    ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    GroupService groupService = mock(GroupService.class);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        user.setUser("user_name");
        group.setName("group_name");
        sudoAuthentication.setSudoPassword("sudo_password");
    }

    @Test
    @DisplayName("""
            Given a group creation process,
            when the user is created successfully,
            then it should return HTTP 200.
        """)
    public void testGroupCreation() throws Exception {
        GroupController groupController = new GroupController(groupService);
        GroupOperationDTO groupOperationDTO = new GroupOperationDTO();
        groupOperationDTO.setGroup(group);
        groupOperationDTO.setSudoAuthentication(sudoAuthentication);
        when(groupService.createGroup(group, sudoAuthentication)).thenReturn(true);
        ResponseEntity<?> response = groupController.groupCreation(groupOperationDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("""
            Given a process of associating a user to a group,
            when the user is added successfully,
            then it should return HTTP 200.
        """)
    public void testAddUserToGroup() throws Exception {
        AssignUserToGroupDTO assignUserToGroupDTO = new AssignUserToGroupDTO();
        assignUserToGroupDTO.setUser(user);
        assignUserToGroupDTO.setGroup(group);
        assignUserToGroupDTO.setSudoAuthentication(sudoAuthentication);
        GroupController groupController = new GroupController(groupService);
        when(groupService.addUserToGroup(group, user, sudoAuthentication)).thenReturn(true);
        ResponseEntity<?> response = groupController.addUserToGroup(assignUserToGroupDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("""
            Given a group creation process,
            when the user is successfully created,
            then it should return HTTP 200
        """)
    public void testUserCreation() throws Exception {
        GroupOperationDTO groupOperationDTO = new GroupOperationDTO();
        groupOperationDTO.setGroup(group);
        groupOperationDTO.setSudoAuthentication(sudoAuthentication);
        GroupController groupController = new GroupController(groupService);
        when(groupService.createGroup(group, sudoAuthentication)).thenReturn(true);
        ResponseEntity<?> response = groupController.groupCreation(groupOperationDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}