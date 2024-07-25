package com.br.SambaWebAPI.folder.factory;

import com.br.SambaWebAPI.folder.enums.FolderCreationErrorCode;
import com.br.SambaWebAPI.folder.exceptions.FolderCreationException;

public class FolderCreationFactory {
    public static FolderCreationException createException() throws FolderCreationException{
        throw new FolderCreationException(FolderCreationErrorCode.GENERIC_ERROR);
    }
}
