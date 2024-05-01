package com.br.SambaWebAPI.exceptions;

import com.br.SambaWebAPI.utils.enums.UserManagent.PasswordCreationErrorCode;

public class PasswordCreationException extends Exception {
    private final PasswordCreationErrorCode errorCode;

    public PasswordCreationException(PasswordCreationErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public PasswordCreationErrorCode getErrorCode() {
        return errorCode;
    }
}