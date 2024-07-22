package com.br.SambaWebAPI.folder.exceptions;

import com.br.SambaWebAPI.folder.enums.FolderCreationErrorCode;

public class FolderCreationException extends Exception{
    private final FolderCreationErrorCode errorCode;

    public FolderCreationException(FolderCreationErrorCode errorCode){
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public FolderCreationErrorCode getErrorCode() {
        return errorCode;
    }


}
