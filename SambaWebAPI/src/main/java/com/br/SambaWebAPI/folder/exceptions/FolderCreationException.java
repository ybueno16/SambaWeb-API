package com.br.SambaWebAPI.folder.exceptions;

import com.br.SambaWebAPI.folder.enums.FolderCreationErrorCode;

public class FolderCreationException {
    private final FolderCreationErrorCode errorCode;

    public FolderCreationErrorCode(FolderCreationErrorCode errorCode){
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public FolderCreationErrorCode getErrorCode() {
        return errorCode;
    }


}
