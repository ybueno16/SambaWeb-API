package com.br.SambaWebAPI.group.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class CreateGroupErrorCode extends ErrorCode {
  public static final CreateGroupErrorCode GENERIC_ERROR =
      new CreateGroupErrorCode(
          "Generic error. An unknown error occurred during user creation.", HttpStatus.BAD_REQUEST);
  public static final CreateGroupErrorCode GID_ALREADY_EXISTS =
      new CreateGroupErrorCode("Group ID already exists.", HttpStatus.INTERNAL_SERVER_ERROR);
  public static final CreateGroupErrorCode GROUP_NAME_NOT_UNIQUE =
      new CreateGroupErrorCode("The group name already exists.", HttpStatus.BAD_REQUEST);
  public static final CreateGroupErrorCode CANT_UPDT_GROUP_FILE =
      new CreateGroupErrorCode(
          "Unable to update the groups configuration file.", HttpStatus.INTERNAL_SERVER_ERROR);

  private CreateGroupErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
