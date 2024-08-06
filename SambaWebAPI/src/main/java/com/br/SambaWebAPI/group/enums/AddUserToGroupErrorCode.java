package com.br.SambaWebAPI.group.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class AddUserToGroupErrorCode extends ErrorCode {
  public static final AddUserToGroupErrorCode UID_ALREADY_EXISTS =
      new AddUserToGroupErrorCode(
          "The UID is already in use (not unique).", HttpStatus.INTERNAL_SERVER_ERROR);
  public static final AddUserToGroupErrorCode LOGIN_DOES_NOT_EXIST =
      new AddUserToGroupErrorCode(
          "The login to be modified does not exist, or the group does not exist.",
          HttpStatus.NOT_FOUND);
  public static final AddUserToGroupErrorCode LOGIN_IN_USE =
      new AddUserToGroupErrorCode("The login to be modified is in use.", HttpStatus.CONFLICT);
  public static final AddUserToGroupErrorCode NEW_LOGNAME_ALREADY_EXISTS =
      new AddUserToGroupErrorCode(
          "The new_logname is already in use.", HttpStatus.INTERNAL_SERVER_ERROR);
  public static final AddUserToGroupErrorCode CANT_UPDATE_GROUP_DB =
      new AddUserToGroupErrorCode(
          "Cannot update the group database.", HttpStatus.INTERNAL_SERVER_ERROR);
  public static final AddUserToGroupErrorCode INSUFFICIENT_SPACE =
      new AddUserToGroupErrorCode(
          "Insufficient space to move the home directory.", HttpStatus.INTERNAL_SERVER_ERROR);
  public static final AddUserToGroupErrorCode CANT_MOVE_HOME_DIR =
      new AddUserToGroupErrorCode(
          "Unable to complete the move of the home directory to the new home directory.",
          HttpStatus.INTERNAL_SERVER_ERROR);
  public static final AddUserToGroupErrorCode GENERIC_ERROR =
      new AddUserToGroupErrorCode(
          "Erro genérico. Ocorreu um erro desconhecido durante a adição de grupo ao usuário.",
          HttpStatus.BAD_REQUEST);

  private AddUserToGroupErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
