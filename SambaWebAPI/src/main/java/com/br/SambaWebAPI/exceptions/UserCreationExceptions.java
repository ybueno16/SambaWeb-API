package com.br.SambaWebAPI.exceptions;

import com.br.SambaWebAPI.utils.enums.UserManagent.UserCreationErrorCode;

public class UserCreationExceptions extends Exception {
    private final UserCreationErrorCode errorCode;

    public UserCreationExceptions(UserCreationErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public UserCreationErrorCode getErrorCode() {
        return errorCode;
    }
}
