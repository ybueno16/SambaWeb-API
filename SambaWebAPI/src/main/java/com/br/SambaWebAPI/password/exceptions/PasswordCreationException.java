package com.br.SambaWebAPI.password.exceptions;

import com.br.SambaWebAPI.password.enums.PasswordCreationErrorCode;

public class PasswordCreationException extends Exception {
  private final PasswordCreationErrorCode errorCode;

  public PasswordCreationException(PasswordCreationErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public PasswordCreationErrorCode getErrorCode() {
    return errorCode;
  }
}
