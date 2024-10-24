package com.br.SambaWebAPI.password.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class SudoAuthentication {
  @JsonProperty("sudoAuthentication")
  @Schema(description = "The sudoer's password", example = "strongpassword")
  private String sudoPassword;

  @Schema(description = "The sudoer's user", example = "root")
  private String sudoUser;

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
