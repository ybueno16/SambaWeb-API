package com.br.SambaWebAPI.user.factory;

import com.br.SambaWebAPI.user.enums.DeleteUserSambaErrorCode;
import com.br.SambaWebAPI.user.exceptions.UserSambaDeleteException;

public class DeleteSambaUserFactory extends Throwable {
  public static UserSambaDeleteException createException(int exitCode)
      throws UserSambaDeleteException {
    throw new UserSambaDeleteException(DeleteUserSambaErrorCode.GENERIC_ERROR);
  }
}
