package com.br.SambaWebAPI.user.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class CreateUserErrorCode extends ErrorCode {
  public static final CreateUserErrorCode CANT_UPDT_PASSWD_FILE =
      new CreateUserErrorCode(
          "It was not possible to update the password file.", HttpStatus.BAD_REQUEST);

  public static final CreateUserErrorCode USR_ALREADY_EXISTS =
      new CreateUserErrorCode(
          "The user you are trying to create already exists", HttpStatus.CONFLICT);

  public static final CreateUserErrorCode CANT_CREATE_HOME_DIR =
      new CreateUserErrorCode(
          "It was not possible to create the home directory", HttpStatus.INTERNAL_SERVER_ERROR);

  public static final CreateUserErrorCode CANT_CREATE_MAIL_SPOOL =
      new CreateUserErrorCode(
          "It was not possible to create the mail spool", HttpStatus.INTERNAL_SERVER_ERROR);

  public static final CreateUserErrorCode CANT_UPDATE_SELINUX =
      new CreateUserErrorCode(
          "It was not possible to update the SELinux user mapping", HttpStatus.BAD_REQUEST);

  public static final CreateUserErrorCode GENERIC_ERROR =
      new CreateUserErrorCode(
          "Generic error. An unknown error occurred during user creation.",
          HttpStatus.INTERNAL_SERVER_ERROR);

  private CreateUserErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
