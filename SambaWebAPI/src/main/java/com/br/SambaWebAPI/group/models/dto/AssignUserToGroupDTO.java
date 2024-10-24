package com.br.SambaWebAPI.group.models.dto;

import com.br.SambaWebAPI.group.models.Group;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;

public class AssignUserToGroupDTO {
  private User user;
  private Group group;
  private SudoAuthentication sudoAuthentication;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public SudoAuthentication getSudoAuthentication() {
    return sudoAuthentication;
  }

  public void setSudoAuthentication(SudoAuthentication sudoAuthentication) {
    this.sudoAuthentication = sudoAuthentication;
  }
}
