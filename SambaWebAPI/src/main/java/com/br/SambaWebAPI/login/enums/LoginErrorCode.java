package com.br.SambaWebAPI.login.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class LoginErrorCode extends ErrorCode {

  public static final LoginErrorCode GENERIC_ERROR =
      new LoginErrorCode(
          "Generic error. An unknown error occurred while logging in.", HttpStatus.BAD_REQUEST);

  private LoginErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
