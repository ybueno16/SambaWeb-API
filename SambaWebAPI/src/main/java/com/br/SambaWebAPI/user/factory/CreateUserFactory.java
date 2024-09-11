package com.br.SambaWebAPI.user.factory;


import com.br.SambaWebAPI.user.exceptions.CreateUserException;
import com.br.SambaWebAPI.user.enums.CreateUserErrorCode;

public class CreateUserFactory extends Throwable {
    public static CreateUserException createException(int exitCode) {
        return switch (exitCode) {
            case 1 -> new CreateUserException(CreateUserErrorCode.CANT_UPDT_PASSWD_FILE);
            case 9 -> new CreateUserException(CreateUserErrorCode.USR_ALREADY_EXISTS);
            case 12 -> new CreateUserException(CreateUserErrorCode.CANT_CREATE_HOME_DIR);
            case 13 -> new CreateUserException(CreateUserErrorCode.CANT_CREATE_MAIL_SPOOL);
            case 14 -> new CreateUserException(CreateUserErrorCode.CANT_UPDATE_SELINUX);
            default -> new CreateUserException(CreateUserErrorCode.GENERIC_ERROR);
        };
    }
}
