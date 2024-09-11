package com.br.SambaWebAPI.user.exceptions;

import com.br.SambaWebAPI.user.enums.DeleteUserErrorCode;

public class DeleteUserException extends Exception {
  private final DeleteUserErrorCode errorCode;

  public DeleteUserException(DeleteUserErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public DeleteUserErrorCode getErrorCode() {
    return errorCode;
  }
}
