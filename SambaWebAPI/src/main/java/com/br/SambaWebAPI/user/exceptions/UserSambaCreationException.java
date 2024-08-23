package com.br.SambaWebAPI.user.exceptions;

import com.br.SambaWebAPI.user.enums.UserSambaCreationErrorCode;
import com.br.SambaWebAPI.user.enums.UserSambaDeleteErrorCode;

public class UserSambaCreationException extends Throwable {
  private final UserSambaCreationErrorCode errorCode;

  public UserSambaCreationException(UserSambaCreationErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public UserSambaCreationErrorCode getErrorCode() {
    return errorCode;
  }
}
