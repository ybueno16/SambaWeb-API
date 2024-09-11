package com.br.SambaWebAPI.user.factory;

import com.br.SambaWebAPI.user.enums.DeleteUserErrorCode;
import com.br.SambaWebAPI.user.exceptions.DeleteUserException;

public class DeleteUserFactory extends Throwable {
  public static DeleteUserException createException(int exitCode) {
    return switch (exitCode) {
      case 1 -> new DeleteUserException(DeleteUserErrorCode.CANT_UPDT_PASSWD_FILE);
      case 6 -> new DeleteUserException(DeleteUserErrorCode.USER_DOESNT_EXIST);
      case 8 -> new DeleteUserException(DeleteUserErrorCode.USER_LOGGED);
      case 10 -> new DeleteUserException(DeleteUserErrorCode.CANT_UPDT_GROUP_FILE);
      case 12 -> new DeleteUserException(DeleteUserErrorCode.CANT_REMOVE_HOME_DIR);

      default -> new DeleteUserException(DeleteUserErrorCode.GENERIC_ERROR);
    };
  }
}
