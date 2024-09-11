package com.br.SambaWebAPI.user.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class CreateSambaUserErrorCode extends ErrorCode {
  public static final CreateSambaUserErrorCode GENERIC_ERROR =
      new CreateSambaUserErrorCode(
          "Generic error. An unknown error occurred while creating the samba user.",
          HttpStatus.BAD_REQUEST);

  private CreateSambaUserErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
