package com.br.SambaWebAPI.user.exceptions;

import com.br.SambaWebAPI.user.enums.CreateSambaUserErrorCode;

public class CreateUserSambaException extends Throwable {
  private final CreateSambaUserErrorCode errorCode;

  public CreateUserSambaException(CreateSambaUserErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public CreateSambaUserErrorCode getErrorCode() {
    return errorCode;
  }
}
