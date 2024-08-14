package com.br.SambaWebAPI.user.factory;

import com.br.SambaWebAPI.user.enums.UserDeleteErrorCode;
import com.br.SambaWebAPI.user.exceptions.UserDeleteException;

public class UserDeleteFactory extends Throwable {
  public static UserDeleteException createException(int exitCode) {
    return switch (exitCode) {
      case 1 -> new UserDeleteException(UserDeleteErrorCode.CANT_UPDT_PASSWD_FILE);
      case 6 -> new UserDeleteException(UserDeleteErrorCode.USER_DOESNT_EXIST);
      case 8 -> new UserDeleteException(UserDeleteErrorCode.USER_LOGGED);
      case 10 -> new UserDeleteException(UserDeleteErrorCode.CANT_UPDT_GROUP_FILE);
      case 12 -> new UserDeleteException(UserDeleteErrorCode.CANT_REMOVE_HOME_DIR);

      default -> new UserDeleteException(UserDeleteErrorCode.GENERIC_ERROR);
    };
  }
}
