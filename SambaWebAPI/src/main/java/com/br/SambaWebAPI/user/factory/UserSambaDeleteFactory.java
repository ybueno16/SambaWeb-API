package com.br.SambaWebAPI.user.factory;

import com.br.SambaWebAPI.user.enums.UserSambaDeleteErrorCode;
import com.br.SambaWebAPI.user.exceptions.UserSambaDeleteException;

public class UserSambaDeleteFactory extends Throwable{
  public static UserSambaDeleteException createException(int exitCode) throws  UserSambaDeleteException {
    throw new UserSambaDeleteException(UserSambaDeleteErrorCode.GENERIC_ERROR);

  }
}
