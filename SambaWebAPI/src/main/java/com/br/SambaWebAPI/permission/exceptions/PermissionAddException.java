package com.br.SambaWebAPI.permission.exceptions;

import com.br.SambaWebAPI.permission.enums.PermissionAddErrorCode;

public class PermissionAddException extends Throwable {
    private final PermissionAddErrorCode errorCode;

    public PermissionAddException(PermissionAddErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public PermissionAddErrorCode getErrorCode() {
        return errorCode;
    }
}
