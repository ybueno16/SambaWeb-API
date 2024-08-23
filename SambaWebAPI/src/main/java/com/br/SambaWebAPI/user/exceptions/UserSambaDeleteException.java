package com.br.SambaWebAPI.user.exceptions;

import com.br.SambaWebAPI.user.enums.UserSambaDeleteErrorCode;

public class UserSambaDeleteException extends Throwable {
  private final UserSambaDeleteErrorCode errorCode;

  public UserSambaDeleteException(UserSambaDeleteErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public UserSambaDeleteErrorCode getErrorCode() {
    return errorCode;
  }
}
