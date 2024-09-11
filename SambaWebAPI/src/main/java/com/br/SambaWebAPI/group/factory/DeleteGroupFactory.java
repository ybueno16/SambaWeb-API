package com.br.SambaWebAPI.group.factory;

import com.br.SambaWebAPI.group.enums.DeleteGroupErrorCode;
import com.br.SambaWebAPI.group.exceptions.DeleteGroupException;

public class DeleteGroupFactory {
  public static DeleteGroupException createException(int exitCode) throws DeleteGroupException {
    switch (exitCode) {
      case 6 -> throw new DeleteGroupException(DeleteGroupErrorCode.GROUP_DOESNT_EXIST);
      case 8 -> throw new DeleteGroupException(DeleteGroupErrorCode.CANT_REMOVE_PRIMARY_GROUP);
      case 10 -> throw new DeleteGroupException(DeleteGroupErrorCode.CANT_UPDT_GROUP_FILE);
      default -> throw new DeleteGroupException(DeleteGroupErrorCode.GENERIC_ERROR);
    }
  }
}
