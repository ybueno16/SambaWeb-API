package com.br.SambaWebAPI.group.exceptions;

import com.br.SambaWebAPI.group.enums.CreateGroupErrorCode;

public class CreateGroupException extends Exception {
  private final CreateGroupErrorCode errorCode;

  public CreateGroupException(CreateGroupErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public CreateGroupErrorCode getErrorCode() {
    return errorCode;
  }
}
