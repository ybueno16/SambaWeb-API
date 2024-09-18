package com.br.SambaWebAPI.group.controllers;

import com.br.SambaWebAPI.group.models.Group;
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
    GroupService groupService = Mockito.mock(GroupService.class);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        user.setUser("user_name");
        group.setName("group_name");
        sudoAuthentication.setSudoPassword("sudo_password");
    }

    private Map<String, Object> getJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(group.getName(), group);
        json.put(sudoAuthentication.getSudoPassword(),sudoAuthentication);
        return json;
    }

    @Test
    @DisplayName("""
                Given a group creation process,
                when the user is created successfully,
                then it should return HTTP 200.
            """)
    public void testGroupCreation() throws Exception {

        GroupController groupController = new GroupController(objectMapper, groupService);

        when(groupService.createGroup(group,sudoAuthentication)).thenReturn(true);

        ResponseEntity<?> response = groupController.groupCreation(getJson());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("""
                Given a process of associating a user to a group,
                when the user is added successfully,
                then it should return HTTP 200.
            """)
    public void testAddUserToGroup() throws Exception {
        Map<String, Object> json = new HashMap<>();
        json.put(user.getUser(), user);
        json.put(group.getName(), group);
        json.put(sudoAuthentication.getSudoPassword(),sudoAuthentication);

        GroupController groupController = new GroupController(objectMapper, groupService);

        when(groupService.addUserToGroup(group, user,sudoAuthentication)).thenReturn(true);

        ResponseEntity<?> response = groupController.addUserToGroup(json);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("""
                Given a group creation process,
                when the user is successfully created,
                then it should return HTTP 200
            """)
    public void testUserCreation() throws Exception {

        GroupController groupController = new GroupController(objectMapper, groupService);

        when(groupService.deleteGroup(group,sudoAuthentication)).thenReturn(true);

        ResponseEntity<?> response = groupController.deleteGroup(getJson());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}