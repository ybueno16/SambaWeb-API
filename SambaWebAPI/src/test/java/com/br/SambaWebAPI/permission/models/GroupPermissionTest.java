package com.br.SambaWebAPI.permission.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupPermissionTest {

    @Test
    void setRead() {
        GroupPermission groupPermission = new GroupPermission();
        groupPermission.setRead(1);
        assertEquals(1, groupPermission.getRead());
    }

    @Test
    void setWrite() {
        GroupPermission groupPermission = new GroupPermission();
        groupPermission.setWrite(1);
        assertEquals(1, groupPermission.getWrite());
    }

    @Test
    void setExecute() {
        GroupPermission groupPermission = new GroupPermission();
        groupPermission.setExecute(1);
        assertEquals(1, groupPermission.getExecute());
    }

    @Test
    void getRead() {
        GroupPermission groupPermission = new GroupPermission();
        groupPermission.setRead(1);
        assertEquals(1, groupPermission.getRead());
    }

    @Test
    void getWrite() {
        GroupPermission groupPermission = new GroupPermission();
        groupPermission.setWrite(1);
        assertEquals(1, groupPermission.getWrite());
    }

    @Test
    void getExecute() {
        GroupPermission groupPermission = new GroupPermission();
        groupPermission.setExecute(1);
        assertEquals(1, groupPermission.getExecute());
    }
}