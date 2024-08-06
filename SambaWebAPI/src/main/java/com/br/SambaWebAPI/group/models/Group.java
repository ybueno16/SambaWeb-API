package com.br.SambaWebAPI.group.models;

import com.br.SambaWebAPI.user.models.User;
import java.util.List;

public class Group {
  private String name;
  private List<User> userList;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<User> getUserList() {
    return userList;
  }

  public void setUserList(List<User> userList) {
    this.userList = userList;
  }
}
