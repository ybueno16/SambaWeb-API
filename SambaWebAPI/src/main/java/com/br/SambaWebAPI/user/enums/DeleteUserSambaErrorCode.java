package com.br.SambaWebAPI.user.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class DeleteUserSambaErrorCode extends ErrorCode {
  public static final DeleteUserSambaErrorCode GENERIC_ERROR =
      new DeleteUserSambaErrorCode(
          "Generic error. An unknown error occurred while removing the user.",
          HttpStatus.BAD_REQUEST);

  private DeleteUserSambaErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
