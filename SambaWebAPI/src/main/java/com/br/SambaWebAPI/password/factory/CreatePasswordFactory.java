package com.br.SambaWebAPI.password.factory;

import com.br.SambaWebAPI.password.enums.CreatePasswordErrorCode;
import com.br.SambaWebAPI.password.exceptions.CreatePasswordException;

public class CreatePasswordFactory {

  public static CreatePasswordException createException(int exitCode)
      throws CreatePasswordException {
    switch (exitCode) {
      case 3 -> throw new CreatePasswordException(CreatePasswordErrorCode.PASSWD_FILE_MISSING);
      case 5 -> throw new CreatePasswordException(CreatePasswordErrorCode.PASSWD_FILE_BUSY);
      default -> throw new CreatePasswordException(CreatePasswordErrorCode.GENERIC_ERROR);
    }
  }
}
