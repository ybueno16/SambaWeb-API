package com.br.SambaWebAPI.exceptions;


import com.br.SambaWebAPI.utils.enums.UserManagent.AddUserToGroupErrorCode;

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

