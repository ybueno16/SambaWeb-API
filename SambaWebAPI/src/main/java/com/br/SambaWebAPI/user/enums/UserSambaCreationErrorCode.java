package com.br.SambaWebAPI.user.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserSambaCreationErrorCode extends ErrorCode {
  public static final UserSambaCreationErrorCode GENERIC_ERROR =
      new UserSambaCreationErrorCode(
          "Erro genérico. Ocorreu um erro desconhecido durante a remoção do usuário.",
          HttpStatus.BAD_REQUEST);

  private UserSambaCreationErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
