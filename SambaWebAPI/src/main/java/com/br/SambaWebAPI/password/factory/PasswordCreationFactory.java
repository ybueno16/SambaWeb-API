package com.br.SambaWebAPI.password.factory;

import com.br.SambaWebAPI.password.exceptions.PasswordCreationException;
import com.br.SambaWebAPI.password.enums.PasswordCreationErrorCode;

public class PasswordCreationFactory {

        public static PasswordCreationException createException(int exitCode) throws PasswordCreationException {
            switch (exitCode) {
                case 2:
                    throw new PasswordCreationException(PasswordCreationErrorCode.UNKNOWN_USER);
                case 252:
                    throw new PasswordCreationException(PasswordCreationErrorCode.UNKNOWN_USERNAME);
                default:
                    throw new PasswordCreationException(PasswordCreationErrorCode.GENERIC_ERROR);
            }
        }


}
