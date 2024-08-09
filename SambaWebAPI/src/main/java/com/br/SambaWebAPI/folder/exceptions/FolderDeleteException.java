package com.br.SambaWebAPI.folder.exceptions;

import com.br.SambaWebAPI.folder.enums.FolderCreationErrorCode;

public class FolderDeleteException extends Exception {
  private final FolderCreationErrorCode errorCode;

  public FolderDeleteException(FolderCreationErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public FolderCreationErrorCode getErrorCode() {
    return errorCode;
  }
}
