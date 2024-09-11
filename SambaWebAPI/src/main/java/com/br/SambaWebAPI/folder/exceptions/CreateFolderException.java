package com.br.SambaWebAPI.folder.exceptions;

import com.br.SambaWebAPI.folder.enums.CreateFolderErrorCode;

public class CreateFolderException extends Exception {
  private final CreateFolderErrorCode errorCode;

  public CreateFolderException(CreateFolderErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public CreateFolderErrorCode getErrorCode() {
    return errorCode;
  }
}
