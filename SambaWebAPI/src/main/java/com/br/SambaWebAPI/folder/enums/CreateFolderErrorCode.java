package com.br.SambaWebAPI.folder.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class CreateFolderErrorCode extends ErrorCode {
  public static final CreateFolderErrorCode GENERIC_ERROR =
      new CreateFolderErrorCode(
          "Erro genérico. Ocorreu um erro desconhecido durante a remoção da pasta.",
          HttpStatus.INTERNAL_SERVER_ERROR);

  private CreateFolderErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
