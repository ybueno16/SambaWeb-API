package com.br.SambaWebAPI.login.exceptions;

import com.br.SambaWebAPI.login.enums.LoginErrorCode;

public class LoginException extends Exception {
  private final LoginErrorCode errorCode;

  public LoginException(LoginErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public LoginErrorCode getErrorCode() {
    return errorCode;
  }
}
