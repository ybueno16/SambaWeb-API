package com.br.SambaWebAPI.password.exceptions;

import com.br.SambaWebAPI.password.enums.CreatePasswordErrorCode;

public class CreatePasswordException extends Exception {
  private final CreatePasswordErrorCode errorCode;

  public CreatePasswordException(CreatePasswordErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public CreatePasswordErrorCode getErrorCode() {
    return errorCode;
  }
}
