package com.br.SambaWebAPI.user.exceptions;

import com.br.SambaWebAPI.user.enums.UserDeleteErrorCode;

public class UserDeleteException extends Exception {
  private final UserDeleteErrorCode errorCode;

  public UserDeleteException(UserDeleteErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public UserDeleteErrorCode getErrorCode() {
    return errorCode;
  }
}
