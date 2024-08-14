package com.br.SambaWebAPI.user.factory;


import com.br.SambaWebAPI.user.exceptions.UserCreationException;
import com.br.SambaWebAPI.user.enums.UserCreationErrorCode;

public class UserCreationFactory extends Throwable {
    public static UserCreationException createException(int exitCode) {
        return switch (exitCode) {
            case 1 -> new UserCreationException(UserCreationErrorCode.CANT_UPDT_PASSWD_FILE);
            case 9 -> new UserCreationException(UserCreationErrorCode.USR_ALREADY_EXISTS);
            case 12 -> new UserCreationException(UserCreationErrorCode.CANT_CREATE_HOME_DIR);
            case 13 -> new UserCreationException(UserCreationErrorCode.CANT_CREATE_MAIL_SPOOL);
            case 14 -> new UserCreationException(UserCreationErrorCode.CANT_UPDATE_SELINUX);
            default -> new UserCreationException(UserCreationErrorCode.GENERIC_ERROR);
        };
    }
}
