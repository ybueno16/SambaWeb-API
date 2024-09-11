package com.br.SambaWebAPI.folder.exceptions;

import com.br.SambaWebAPI.folder.enums.CreateFolderErrorCode;

public class DeleteFolderException extends Exception {
  private final CreateFolderErrorCode errorCode;

  public DeleteFolderException(CreateFolderErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public CreateFolderErrorCode getErrorCode() {
    return errorCode;
  }
}
