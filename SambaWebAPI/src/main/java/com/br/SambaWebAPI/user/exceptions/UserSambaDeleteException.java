package com.br.SambaWebAPI.user.exceptions;

import com.br.SambaWebAPI.user.enums.DeleteUserSambaErrorCode;

public class UserSambaDeleteException extends Throwable {
  private final DeleteUserSambaErrorCode errorCode;

  public UserSambaDeleteException(DeleteUserSambaErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public DeleteUserSambaErrorCode getErrorCode() {
    return errorCode;
  }
}
