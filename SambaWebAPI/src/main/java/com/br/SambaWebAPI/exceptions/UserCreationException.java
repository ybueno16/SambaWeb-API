package com.br.SambaWebAPI.exceptions;

import com.br.SambaWebAPI.utils.enums.UserManagent.PasswordCreationErrorCode;
import com.br.SambaWebAPI.utils.enums.UserManagent.UserCreationErrorCode;

public class UserCreationException extends Exception {
    private final UserCreationErrorCode errorCode;

    public UserCreationException(UserCreationErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public UserCreationErrorCode getErrorCode() {
        return errorCode;
    }
}