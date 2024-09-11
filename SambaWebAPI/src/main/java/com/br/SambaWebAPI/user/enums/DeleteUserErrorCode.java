package com.br.SambaWebAPI.user.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class DeleteUserErrorCode extends ErrorCode {
  public static final DeleteUserErrorCode GENERIC_ERROR =
          new DeleteUserErrorCode(
                  "Generic error. An unknown error occurred during user deletion.",
                  HttpStatus.BAD_REQUEST);

  public static final DeleteUserErrorCode CANT_UPDT_PASSWD_FILE =
          new DeleteUserErrorCode(
                  "It was not possible to update the password file.",
                  HttpStatus.INTERNAL_SERVER_ERROR);

  public static final DeleteUserErrorCode USER_DOESNT_EXIST =
          new DeleteUserErrorCode(
                  "The specified user does not exist.",
                  HttpStatus.CONFLICT);

  public static final DeleteUserErrorCode USER_LOGGED =
          new DeleteUserErrorCode(
                  "The user is currently logged in.",
                  HttpStatus.CONFLICT);

  public static final DeleteUserErrorCode CANT_UPDT_GROUP_FILE =
          new DeleteUserErrorCode(
                  "It was not possible to update the group file.",
                  HttpStatus.INTERNAL_SERVER_ERROR);

  public static final DeleteUserErrorCode CANT_REMOVE_HOME_DIR =
          new DeleteUserErrorCode(
                  "It was not possible to delete the home directory.",
                  HttpStatus.INTERNAL_SERVER_ERROR);

  private DeleteUserErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
