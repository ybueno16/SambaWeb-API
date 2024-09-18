package com.br.SambaWebAPI.folder.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class DeleteFolderErrorCode extends ErrorCode {
  public static final DeleteFolderErrorCode GENERIC_ERROR =
      new DeleteFolderErrorCode(
          "Generic error. An unknown error occurred while removing the folder.",
          HttpStatus.INTERNAL_SERVER_ERROR);

  private DeleteFolderErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
