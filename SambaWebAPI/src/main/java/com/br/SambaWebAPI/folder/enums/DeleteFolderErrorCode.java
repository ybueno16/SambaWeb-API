package com.br.SambaWebAPI.folder.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class DeleteFolderErrorCode extends ErrorCode {
  public static final DeleteFolderErrorCode GENERIC_ERROR =
      new DeleteFolderErrorCode(
          "Erro genérico. Ocorreu um erro desconhecido durante a criação da pasta.",
          HttpStatus.INTERNAL_SERVER_ERROR);

  private DeleteFolderErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
