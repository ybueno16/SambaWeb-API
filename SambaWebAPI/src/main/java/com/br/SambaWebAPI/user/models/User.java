package com.br.SambaWebAPI.user.models;

import com.br.SambaWebAPI.group.models.Group;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User {
  @JsonProperty("user")
  private String user;
  private String password;
  private List<Group> groupList;

  public List<Group> getGroupList() {
    return groupList;
  }

  public void setGroupList(List<Group> groupList) {
    this.groupList = groupList;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    if (password == null) {
      throw new NullPointerException("Senha não pode ser nula. Por favor, forneça uma senha válida.");
    }
    this.password = password;
  }
}
