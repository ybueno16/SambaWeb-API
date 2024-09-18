package com.br.SambaWebAPI.group.factory;

import com.br.SambaWebAPI.group.enums.AddUserToGroupErrorCode;
import com.br.SambaWebAPI.group.exceptions.AddUserToGroupException;

public class AddUserToGroupFactory {
  public static AddUserToGroupException createException(int exitCode)
      throws AddUserToGroupException {
      switch (exitCode) {
          case 4 -> throw new AddUserToGroupException(AddUserToGroupErrorCode.UID_ALREADY_EXISTS);
          case 6 -> throw new AddUserToGroupException(AddUserToGroupErrorCode.LOGIN_DOES_NOT_EXIST);
          case 8 -> throw new AddUserToGroupException(AddUserToGroupErrorCode.LOGIN_IN_USE);
          case 9 -> throw new AddUserToGroupException(AddUserToGroupErrorCode.NEW_LOGNAME_ALREADY_EXISTS);
          case 10 -> throw new AddUserToGroupException(AddUserToGroupErrorCode.CANT_UPDATE_GROUP_DB);
          case 11 -> throw new AddUserToGroupException(AddUserToGroupErrorCode.INSUFFICIENT_SPACE);
          case 12 -> throw new AddUserToGroupException(AddUserToGroupErrorCode.CANT_MOVE_HOME_DIR);
          default -> throw new AddUserToGroupException(AddUserToGroupErrorCode.GENERIC_ERROR);
      }
  }
}
