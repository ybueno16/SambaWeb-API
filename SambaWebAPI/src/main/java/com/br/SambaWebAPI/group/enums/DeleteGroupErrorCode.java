package com.br.SambaWebAPI.group.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class DeleteGroupErrorCode extends ErrorCode {
  public static final DeleteGroupErrorCode GENERIC_ERROR =
      new DeleteGroupErrorCode(
          "Generic error. An unknown error occurred while removing the group.",
          HttpStatus.BAD_REQUEST);
  public static final DeleteGroupErrorCode GROUP_DOESNT_EXIST =
      new DeleteGroupErrorCode("The specified group does not exist.", HttpStatus.BAD_REQUEST);
  public static final DeleteGroupErrorCode CANT_REMOVE_PRIMARY_GROUP =
      new DeleteGroupErrorCode(
          "The primary group could not be excluded.", HttpStatus.INTERNAL_SERVER_ERROR);
  public static final DeleteGroupErrorCode CANT_UPDT_GROUP_FILE =
      new DeleteGroupErrorCode(
          "Unable to update the groups file.", HttpStatus.BAD_REQUEST);

  protected DeleteGroupErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
