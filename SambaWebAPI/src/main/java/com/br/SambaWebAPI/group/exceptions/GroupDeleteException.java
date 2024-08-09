package com.br.SambaWebAPI.group.exceptions;

import com.br.SambaWebAPI.group.enums.GroupDeleteErrorCode;

public class GroupDeleteException extends Exception {
  private final GroupDeleteErrorCode errorCode;

  public GroupDeleteException(GroupDeleteErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public GroupDeleteErrorCode getErrorCode() {
    return errorCode;
  }
}
