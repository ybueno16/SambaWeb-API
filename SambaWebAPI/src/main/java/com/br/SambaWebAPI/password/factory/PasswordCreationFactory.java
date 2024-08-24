package com.br.SambaWebAPI.password.factory;

import com.br.SambaWebAPI.password.enums.PasswordCreationErrorCode;
import com.br.SambaWebAPI.password.exceptions.PasswordCreationException;

public class PasswordCreationFactory {

  public static PasswordCreationException createException(int exitCode)
      throws PasswordCreationException {
    switch (exitCode) {
      case 3 -> throw new PasswordCreationException(PasswordCreationErrorCode.PASSWD_FILE_MISSING);
      case 5 -> throw new PasswordCreationException(PasswordCreationErrorCode.PASSWD_FILE_BUSY);
      default -> throw new PasswordCreationException(PasswordCreationErrorCode.GENERIC_ERROR);
    }
  }
}
