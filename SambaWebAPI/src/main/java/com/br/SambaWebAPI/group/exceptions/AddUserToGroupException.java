package com.br.SambaWebAPI.group.exceptions;


import com.br.SambaWebAPI.group.enums.AddUserToGroupErrorCode;

public class AddUserToGroupException extends Exception {
    private final AddUserToGroupErrorCode errorCode;

    public AddUserToGroupException(AddUserToGroupErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public AddUserToGroupErrorCode getErrorCode() {
        return errorCode;
    }
}

