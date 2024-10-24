package com.br.SambaWebAPI.user.models;

import com.br.SambaWebAPI.group.models.Group;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class User {

  @JsonProperty("user")
  @Schema(description = "The user's name", example = "sambauser")
  private String user;

  @Schema(description = "The user's passsword", example = "strongpassword123")
  private String password;

  @Schema(description = "The user's groups", example = "[group1,group2]")
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
      throw new NullPointerException(
          "The password cannot be null. Please provide a valid password.");
    }
    this.password = password;
  }
}
