package com.br.SambaWebAPI.user.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserSambaDeleteErrorCode extends ErrorCode {
  public static final UserSambaDeleteErrorCode GENERIC_ERROR =
      new UserSambaDeleteErrorCode(
          "Erro genérico. Ocorreu um erro desconhecido durante a remoção do usuário.",
          HttpStatus.BAD_REQUEST);

  private UserSambaDeleteErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
