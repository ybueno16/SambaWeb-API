package com.br.SambaWebAPI.permission.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PublicPermissionTest {

  @Test
  void setRead() {
    PublicPermission publicPermission = new PublicPermission();
    publicPermission.setRead(1);
    assertEquals(1, publicPermission.getRead());
  }

  @Test
  void getRead() {
    PublicPermission publicPermission = new PublicPermission();
    publicPermission.setRead(1);
    assertEquals(1, publicPermission.getRead());
  }

  @Test
  void setWrite() {
    PublicPermission publicPermission = new PublicPermission();
    publicPermission.setWrite(1);
    assertEquals(1, publicPermission.getWrite());
  }

  @Test
  void getWrite() {
    PublicPermission publicPermission = new PublicPermission();
    publicPermission.setWrite(1);
    assertEquals(1, publicPermission.getWrite());
  }

  @Test
  void setExecute() {
    PublicPermission publicPermission = new PublicPermission();
    publicPermission.setExecute(1);
    assertEquals(1, publicPermission.getExecute());
  }

  @Test
  void getExecute() {
    PublicPermission publicPermission = new PublicPermission();
    publicPermission.setExecute(1);
    assertEquals(1, publicPermission.getExecute());
  }
}
