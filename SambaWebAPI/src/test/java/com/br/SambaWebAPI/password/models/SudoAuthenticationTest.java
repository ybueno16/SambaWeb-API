package com.br.SambaWebAPI.password.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SudoAuthenticationTest {
  @Test
  @DisplayName("Verifica Getters e Setters")
  public void testGettersAndSetters() {
    SudoAuthentication sudoAuth = new SudoAuthentication();
    String sudoUser = "usuario_teste";
    String sudoPassword = "senha_teste";

    sudoAuth.setSudoUser(sudoUser);
    sudoAuth.setSudoPassword(sudoPassword);

    assertEquals(sudoUser, sudoAuth.getSudoUser());
    assertEquals(sudoPassword, sudoAuth.getSudoPassword());
  }

  @Test
  @DisplayName("Check for null values")
  public void testNullValues() {
    SudoAuthentication sudoAuth = new SudoAuthentication();

    assertNull(sudoAuth.getSudoUser());
    assertNull(sudoAuth.getSudoPassword());
  }

  @Test
  @DisplayName("Check for empty values")
  public void testEmptyValues() {
    SudoAuthentication sudoAuth = new SudoAuthentication();
    String sudoUser = "";
    String sudoPassword = "";

    sudoAuth.setSudoUser(sudoUser);
    sudoAuth.setSudoPassword(sudoPassword);

    assertEquals(sudoUser, sudoAuth.getSudoUser());
    assertEquals(sudoPassword, sudoAuth.getSudoPassword());
  }
}
