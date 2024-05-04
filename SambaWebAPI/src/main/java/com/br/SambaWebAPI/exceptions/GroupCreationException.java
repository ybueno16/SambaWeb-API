package com.br.SambaWebAPI.exceptions;


import com.br.SambaWebAPI.utils.enums.UserManagent.GroupCreationErrorCode;

public class GroupCreationException extends Exception{
    private final GroupCreationErrorCode errorCode;

    public GroupCreationException(GroupCreationErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public GroupCreationErrorCode getErrorCode() {
        return errorCode;
    }
}
