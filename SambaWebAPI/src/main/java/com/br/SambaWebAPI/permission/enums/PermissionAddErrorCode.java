package com.br.SambaWebAPI.permission.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class PermissionAddErrorCode extends ErrorCode {

  public static final PermissionAddErrorCode GENERIC_ERROR =
      new PermissionAddErrorCode(
          "Generic error. An unknown error occurred while creating the user password.",
          HttpStatus.BAD_REQUEST);

  private PermissionAddErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
