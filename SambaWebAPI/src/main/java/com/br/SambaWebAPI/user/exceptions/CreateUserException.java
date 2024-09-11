package com.br.SambaWebAPI.user.exceptions;

import com.br.SambaWebAPI.user.enums.CreateUserErrorCode;

public class CreateUserException extends Exception {
  private final CreateUserErrorCode errorCode;

  public CreateUserException(CreateUserErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public CreateUserErrorCode getErrorCode() {
    return errorCode;
  }
}
