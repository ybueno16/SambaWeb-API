package com.br.SambaWebAPI.group.exceptions;

import com.br.SambaWebAPI.group.enums.DeleteGroupErrorCode;

public class DeleteGroupException extends Exception {
  private final DeleteGroupErrorCode errorCode;

  public DeleteGroupException(DeleteGroupErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public DeleteGroupErrorCode getErrorCode() {
    return errorCode;
  }
}
