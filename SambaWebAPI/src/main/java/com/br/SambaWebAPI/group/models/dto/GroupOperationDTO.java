package com.br.SambaWebAPI.group.models.dto;

import com.br.SambaWebAPI.group.models.Group;
import com.br.SambaWebAPI.password.models.SudoAuthentication;

public class GroupOperationDTO {
  private Group group;
  private SudoAuthentication sudoAuthentication;

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
