package com.br.SambaWebAPI.password.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class PasswordCreationErrorCode extends ErrorCode {
  public static final PasswordCreationErrorCode GENERIC_ERROR =
      new PasswordCreationErrorCode(
          "Erro genérico. Ocorreu um erro desconhecido durante a criação da senha do usuario.",
          HttpStatus.BAD_REQUEST);
  public static final PasswordCreationErrorCode UNKNOWN_USER =
      new PasswordCreationErrorCode(
          "Não foi possível encontrar o usuário no sistema", HttpStatus.BAD_REQUEST);
  public static final PasswordCreationErrorCode UNKNOWN_USERNAME =
      new PasswordCreationErrorCode("O Usuário não existe", HttpStatus.BAD_REQUEST);

  private PasswordCreationErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
