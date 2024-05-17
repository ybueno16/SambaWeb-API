package com.br.SambaWebAPI.user.exceptions;

import com.br.SambaWebAPI.user.enums.UserCreationErrorCode;

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