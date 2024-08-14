package com.br.SambaWebAPI.folder.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class FolderCreationErrorCode extends ErrorCode {
  public static final FolderCreationErrorCode GENERIC_ERROR =
      new FolderCreationErrorCode(
          "Erro genérico. Ocorreu um erro desconhecido durante a remoção da pasta.",
          HttpStatus.INTERNAL_SERVER_ERROR);

  private FolderCreationErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
