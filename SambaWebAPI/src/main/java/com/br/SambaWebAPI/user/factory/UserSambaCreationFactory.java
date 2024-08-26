package com.br.SambaWebAPI.user.factory;

import com.br.SambaWebAPI.user.enums.UserSambaCreationErrorCode;
import com.br.SambaWebAPI.user.exceptions.UserSambaCreationException;

public class UserSambaCreationFactory extends Throwable {
  public static UserSambaCreationException createException(int exitCode)
      throws UserSambaCreationException {
    throw new UserSambaCreationException(UserSambaCreationErrorCode.GENERIC_ERROR);
  }
}
