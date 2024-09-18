package com.br.SambaWebAPI.group.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.br.SambaWebAPI.user.models.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GroupTest {

  @Test
  @DisplayName("Check Getters and Setters")
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
  @DisplayName("Tests whether the group list is updated correctly")
  public void testSetGroupListUpdatesValueCorrectly() {
    Group group = new Group();
    List<User> groupList = new ArrayList<>();
    groupList.add(new User());
    group.setUserList(groupList);
    assertEquals(groupList, group.getUserList());
  }

  @Test
  @DisplayName("Tests whether the group list is null by default")
  public void testGetGroupListReturnsNullByDefault() {
    Group group = new Group();
    assertEquals(null, group.getUserList());
  }

  @Test
  @DisplayName("Tests whether the group name is returned correctly.")
  public void testGetGroupNameReturnsCorrectValue() {
    Group group = new Group();
    group.setName("group_teste");
    assertEquals("group_teste", group.getName());
  }

  @Test
  @DisplayName("Tests whether the group name is updated correctly.")
  public void testSetGroupNameUpdatesValueCorrectly() {
    Group group = new Group();
    group.setName("group_teste");
    assertEquals("group_teste", group.getName());
  }

  @Test
  @DisplayName("Tests whether the group name is null by default.")
  public void testGetGroupNameReturnsNullByDefault() {
    Group group = new Group();
    assertEquals(null, group.getName());
  }

  @Test
  @DisplayName("Tests whether the user list is returned correctly.")
  public void testGetUserListReturnsCorrectValue() {
    Group group = new Group();
    List<User> userList = new ArrayList<>();
    userList.add(new User());
    group.setUserList(userList);
    assertEquals(userList, group.getUserList());
  }

  @Test
  @DisplayName("Tests whether the user list is null by default.")
  public void testGetUserListReturnsNullByDefault() {
    Group group = new Group();
    assertEquals(null, group.getUserList());
  }
}
