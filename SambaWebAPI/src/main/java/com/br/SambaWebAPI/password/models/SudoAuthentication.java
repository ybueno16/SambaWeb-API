package com.br.SambaWebAPI.password.models;

public class SudoAuthentication {
  private String sudoPassword;
  private String sudoUser;

    public SudoAuthentication(String sudoAuthentication) {
    }

    public String getSudoUser() {
    return sudoUser;
  }

  public String getSudoPassword() {
    return sudoPassword;
  }

  public void setSudoUser(String sudoUser) {
    this.sudoUser = sudoUser;
  }

  public void setSudoPassword(String sudoPassword) {
    this.sudoPassword = sudoPassword;
  }
}
