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
    user.setUser("test");
  }

  @Test
  @DisplayName("Tests wether user is null by default")
  public void testGetUserReturnsCorrectValue() {
    assertThat(user.getUser(), is("test"));
  }

  @Test
  @DisplayName("Tests wether user is updated correctly")
  public void testSetUserUpdatesValueCorrectly() {
    user.setUser("newTest");
    assertThat(user.getUser(), is("newTest"));
  }

  @Test
  @DisplayName("Tests wether password is null by default")
  public void testGetPasswordReturnsNullByDefault() {
    assertNull(user.getPassword());
  }

  @Test
  @DisplayName("Tests wether password is updated correctly")
  public void testSetPasswordUpdatesValueCorrectly() {
    user.setPassword("password123");
    assertThat(user.getPassword(), is("password123"));
  }

  @Test
  @DisplayName("Tests wether group list is null by default")
  public void testGetGroupListReturnsNullByDefault() {
    assertNull(user.getGroupList());
  }

  @Test
  @DisplayName("Tests whether the group list is updated correctly")
  public void testSetGroupListUpdatesValueCorrectly() {
    Group group = new Group();
    List<Group> groupList = new ArrayList<>();
    groupList.add(group);
    user.setGroupList(groupList);
    assertThat(user.getGroupList(), is(groupList));
  }

  @Test
  @DisplayName("Tests wether password rejects null value")
  public void testSetPasswordRejectsNullValue() {
    assertThrows(NullPointerException.class, () -> user.setPassword(null));
  }
}
