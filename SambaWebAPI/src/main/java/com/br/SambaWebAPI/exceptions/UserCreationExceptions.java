package com.br.SambaWebAPI.exceptions;

import com.br.SambaWebAPI.utils.enums.UserCreationErrorCodeEnum;

public class UserCreationExceptions extends Exception {
    private final UserCreationErrorCodeEnum errorCode;

    public UserCreationExceptions(UserCreationErrorCodeEnum errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public UserCreationErrorCodeEnum getErrorCode() {
        return errorCode;
    }
}
