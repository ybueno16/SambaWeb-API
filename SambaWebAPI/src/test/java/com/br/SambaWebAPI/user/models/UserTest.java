package com.br.SambaWebAPI.user.models;

import com.br.SambaWebAPI.group.models.Group;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = new User("teste");
    }

    @Test
    public void testGetUser() {
        assertEquals("teste", user.getUser());
    }

    @Test
    public void testSetUser() {
        user.setUser("novoTeste");
        assertEquals("novoTeste", user.getUser());
    }

    @Test
    public void testGetPassword() {
        assertNull(user.getPassword());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("senha123");
        assertEquals("senha123", user.getPassword());
    }

    @Test
    public void testGetGroupList() {
        assertNull(user.getGroupList());
    }

    @Test
    public void testSetGroupList() {
        Group group = new Group();
        List<Group> groupList = new ArrayList<>();
        groupList.add(group);
        user.setGroupList(groupList);
        assertEquals(groupList, user.getGroupList());
    }

}