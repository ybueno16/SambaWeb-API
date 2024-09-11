package com.br.SambaWebAPI.group.factory;

import com.br.SambaWebAPI.group.enums.CreateGroupErrorCode;
import com.br.SambaWebAPI.group.exceptions.CreateGroupException;

public class CreateGroupFactory {
  public static CreateGroupException createException(int exitCode) throws CreateGroupException {
    switch (exitCode) {
      case 4:
        throw new CreateGroupException(CreateGroupErrorCode.GID_ALREADY_EXISTS);
      case 9:
        throw new CreateGroupException(CreateGroupErrorCode.GROUP_NAME_NOT_UNIQUE);
      case 10:
        throw new CreateGroupException(CreateGroupErrorCode.CANT_UPDT_GROUP_FILE);
      default:
        throw new CreateGroupException(CreateGroupErrorCode.GENERIC_ERROR);
    }
  }
}
