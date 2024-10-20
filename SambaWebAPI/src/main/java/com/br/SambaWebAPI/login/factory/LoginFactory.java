package com.br.SambaWebAPI.login.factory;

import com.br.SambaWebAPI.login.enums.LoginErrorCode;
import com.br.SambaWebAPI.login.exceptions.LoginException;

public class LoginFactory extends Throwable {

  public static LoginException createException(int exitCode) throws LoginException {
    throw new LoginException(LoginErrorCode.GENERIC_ERROR);
  }
}
