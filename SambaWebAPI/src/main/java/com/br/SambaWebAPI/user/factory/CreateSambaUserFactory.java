package com.br.SambaWebAPI.user.factory;

import com.br.SambaWebAPI.user.enums.CreateSambaUserErrorCode;
import com.br.SambaWebAPI.user.exceptions.CreateUserSambaException;

public class CreateSambaUserFactory extends Throwable {
  public static CreateUserSambaException createException(int exitCode)
      throws CreateUserSambaException {
    throw new CreateUserSambaException(CreateSambaUserErrorCode.GENERIC_ERROR);
  }
}
