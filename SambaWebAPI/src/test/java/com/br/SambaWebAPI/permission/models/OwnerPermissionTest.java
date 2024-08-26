package com.br.SambaWebAPI.permission.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwnerPermissionTest {

    @Test
    void getRead() {
        OwnerPermission ownerPermission = new OwnerPermission();
        ownerPermission.setRead(1);
        assertEquals(1, ownerPermission.getRead());
    }

    @Test
    void setRead() {
        OwnerPermission ownerPermission = new OwnerPermission();
        ownerPermission.setRead(1);
        assertEquals(1, ownerPermission.getRead());
    }

    @Test
    void getWrite() {
        OwnerPermission ownerPermission = new OwnerPermission();
        ownerPermission.setWrite(1);
        assertEquals(1, ownerPermission.getWrite());
    }

    @Test
    void setWrite() {
        OwnerPermission ownerPermission = new OwnerPermission();
        ownerPermission.setWrite(1);
        assertEquals(1, ownerPermission.getWrite());
    }

    @Test
    void getExecute() {
        OwnerPermission ownerPermission = new OwnerPermission();
        ownerPermission.setExecute(1);
        assertEquals(1, ownerPermission.getExecute());
    }

    @Test
    void setExecute() {
        OwnerPermission ownerPermission = new OwnerPermission();
        ownerPermission.setExecute(1);
        assertEquals(1, ownerPermission.getExecute());
    }
}