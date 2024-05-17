package com.br.SambaWebAPI.user.factory;


import com.br.SambaWebAPI.user.exceptions.UserCreationException;
import com.br.SambaWebAPI.user.enums.UserCreationErrorCode;

public class UserCreationFactory extends Throwable {
    public static UserCreationException createException(int exitCode) {
        switch (exitCode) {
            case 1:
                return new UserCreationException(UserCreationErrorCode.CANT_UPDT_PASSWD_FILE);
            case 4:
                return new UserCreationException(UserCreationErrorCode.USR_ALREADY_EXISTS);
            case 6:
                return new UserCreationException(UserCreationErrorCode.SPECIFIED_GROUP_DONT_EXIST);
            case 9:
                return new UserCreationException(UserCreationErrorCode.USR_ALREADY_IN_USE);
            case 10:
                return new UserCreationException(UserCreationErrorCode.CANT_UPDT_GROUP_FILE);
            case 12:
                return new UserCreationException(UserCreationErrorCode.CANT_CREATE_HOME_DIR);
            case 13:
                return new UserCreationException(UserCreationErrorCode.CANT_CREATE_MAIL_SPOOL);
            case 14:
                return new UserCreationException(UserCreationErrorCode.CANT_UPDATE_SELINUX);
            default:
                return new UserCreationException(UserCreationErrorCode.GENERIC_ERROR);
        }
    }
}
