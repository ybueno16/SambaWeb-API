package com.br.SambaWebAPI.group.factory;

import com.br.SambaWebAPI.group.enums.GroupDeleteErrorCode;
import com.br.SambaWebAPI.group.exceptions.GroupDeleteException;

public class GroupDeleteFactory {
  public static GroupDeleteException createException(int exitCode) throws GroupDeleteException {
    switch (exitCode) {
      case 6 -> throw new GroupDeleteException(GroupDeleteErrorCode.GROUP_DOESNT_EXIST);
      case 8 -> throw new GroupDeleteException(GroupDeleteErrorCode.CANT_REMOVE_PRIMARY_GROUP);
      case 10 -> throw new GroupDeleteException(GroupDeleteErrorCode.CANT_UPDT_GROUP_FILE);
      default -> throw new GroupDeleteException(GroupDeleteErrorCode.GENERIC_ERROR);
    }
  }
}
