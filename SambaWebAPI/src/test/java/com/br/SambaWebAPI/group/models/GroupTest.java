package com.br.SambaWebAPI.group.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.br.SambaWebAPI.user.models.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GroupTest {

  @Test
  @DisplayName("Verifica Getters e Setters")
  public void testGettersAndSetters() {
    Group group = new Group();
    String groupName = "group_teste";
    List<User> userList = new ArrayList<>();
    group.setName(groupName);
    group.setUserList(userList);
    assertEquals(groupName, group.getName());
    assertEquals(userList, group.getUserList());
  }

  @Test
  @DisplayName("Testa se a lista de grupos é atualizada corretamente")
  public void testSetGroupListUpdatesValueCorrectly() {
    Group group = new Group();
    List<User> groupList = new ArrayList<>();
    groupList.add(new User());
    group.setUserList(groupList);
    assertEquals(groupList, group.getUserList());
  }

  @Test
  @DisplayName("Testa se a lista de grupos é nula por padrão")
  public void testGetGroupListReturnsNullByDefault() {
    Group group = new Group();
    assertEquals(null, group.getUserList());
  }

  @Test
  @DisplayName("Testa se o nome é retornado corretamente")
  public void testGetGroupNameReturnsCorrectValue() {
    Group group = new Group();
    group.setName("group_teste");
    assertEquals("group_teste", group.getName());
  }

  @Test
  @DisplayName("Testa se o nome é atualizado corretamente")
  public void testSetGroupNameUpdatesValueCorrectly() {
    Group group = new Group();
    group.setName("group_teste");
    assertEquals("group_teste", group.getName());
  }

  @Test
  @DisplayName("Testa se o nome é nulo por padrão")
  public void testGetGroupNameReturnsNullByDefault() {
    Group group = new Group();
    assertEquals(null, group.getName());
  }

  @Test
  @DisplayName("Testa se a lista de usuários é retornado corretamente")
  public void testGetUserListReturnsCorrectValue() {
    Group group = new Group();
    List<User> userList = new ArrayList<>();
    userList.add(new User());
    group.setUserList(userList);
    assertEquals(userList, group.getUserList());
  }

  @Test
  @DisplayName("Testa se a lista de usuários é nula por padrão")
  public void testGetUserListReturnsNullByDefault() {
    Group group = new Group();
    assertEquals(null, group.getUserList());
  }
}
