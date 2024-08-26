package com.br.SambaWebAPI.user.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import com.br.SambaWebAPI.group.models.Group;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {
  private User user;

  @BeforeEach
  public void setUp() {
    user = new User();
    user.setUser("teste");
  }

  @Test
  @DisplayName("Testa se o usuário é retornado corretamente")
  public void testGetUserReturnsCorrectValue() {
    assertThat(user.getUser(), is("teste"));
  }

  @Test
  @DisplayName("Testa se o usuário é atualizado corretamente")
  public void testSetUserUpdatesValueCorrectly() {
    user.setUser("novoTeste");
    assertThat(user.getUser(), is("novoTeste"));
  }

  @Test
  @DisplayName("Testa se a senha é nula por padrão")
  public void testGetPasswordReturnsNullByDefault() {
    assertNull(user.getPassword());
  }

  @Test
  @DisplayName("Testa se a senha é atualizada corretamente")
  public void testSetPasswordUpdatesValueCorrectly() {
    user.setPassword("senha123");
    assertThat(user.getPassword(), is("senha123"));
  }

  @Test
  @DisplayName("Testa se a lista de grupos é nula por padrão")
  public void testGetGroupListReturnsNullByDefault() {
    assertNull(user.getGroupList());
  }

  @Test
  @DisplayName("Testa se a lista de grupos é atualizada corretamente")
  public void testSetGroupListUpdatesValueCorrectly() {
    Group group = new Group();
    List<Group> groupList = new ArrayList<>();
    groupList.add(group);
    user.setGroupList(groupList);
    assertThat(user.getGroupList(), is(groupList));
  }

  @Test
  @DisplayName("Testa se a senha nula é rejeitada")
  public void testSetPasswordRejectsNullValue() {
    assertThrows(NullPointerException.class, () -> user.setPassword(null));
  }
}
