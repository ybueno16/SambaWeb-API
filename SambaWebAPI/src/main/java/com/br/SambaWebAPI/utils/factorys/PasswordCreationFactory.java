package com.br.SambaWebAPI.utils.factorys;

import com.br.SambaWebAPI.exceptions.PasswordCreationException;
import com.br.SambaWebAPI.utils.enums.UserManagent.PasswordCreationErrorCode;

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
