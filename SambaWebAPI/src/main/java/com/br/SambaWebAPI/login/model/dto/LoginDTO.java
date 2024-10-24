package com.br.SambaWebAPI.login.model.dto;

import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;

public class LoginDTO {
  private User user;
  private SudoAuthentication sudoAuthentication;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public SudoAuthentication getSudoAuthentication() {
    return sudoAuthentication;
  }

  public void setSudoAuthentication(SudoAuthentication sudoAuthentication) {
    this.sudoAuthentication = sudoAuthentication;
  }
}
