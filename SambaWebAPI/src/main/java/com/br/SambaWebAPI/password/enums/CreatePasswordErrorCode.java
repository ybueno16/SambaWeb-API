package com.br.SambaWebAPI.password.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class CreatePasswordErrorCode extends ErrorCode {
  public static final CreatePasswordErrorCode GENERIC_ERROR =
      new CreatePasswordErrorCode(
          "Generic error. An unknown error occurred while creating the user's password.",
          HttpStatus.BAD_REQUEST);
  public static final CreatePasswordErrorCode PASSWD_FILE_MISSING =
      new CreatePasswordErrorCode(
          "Password file not found. The password file was not found on the system.",
          HttpStatus.BAD_REQUEST);
  public static final CreatePasswordErrorCode PASSWD_FILE_BUSY =
      new CreatePasswordErrorCode(
          "Password file is busy. The password file is currently being used.",
          HttpStatus.BAD_REQUEST);

  private CreatePasswordErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
