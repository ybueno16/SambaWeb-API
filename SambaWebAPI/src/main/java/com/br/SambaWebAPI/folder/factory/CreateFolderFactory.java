package com.br.SambaWebAPI.folder.factory;

import com.br.SambaWebAPI.folder.enums.CreateFolderErrorCode;
import com.br.SambaWebAPI.folder.exceptions.CreateFolderException;

public class CreateFolderFactory {
  public static CreateFolderException createException() throws CreateFolderException {
    throw new CreateFolderException(CreateFolderErrorCode.GENERIC_ERROR);
  }
}
