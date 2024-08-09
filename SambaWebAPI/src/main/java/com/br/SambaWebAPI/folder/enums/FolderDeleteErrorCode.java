package com.br.SambaWebAPI.folder.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class FolderDeleteErrorCode extends ErrorCode {
  public static final FolderDeleteErrorCode GENERIC_ERROR =
      new FolderDeleteErrorCode(
          "Erro genérico. Ocorreu um erro desconhecido durante a criação da pasta.",
          HttpStatus.INTERNAL_SERVER_ERROR);

  private FolderDeleteErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
