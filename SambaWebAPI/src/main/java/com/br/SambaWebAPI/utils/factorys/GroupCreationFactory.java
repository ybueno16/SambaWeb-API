package com.br.SambaWebAPI.utils.factorys;

import com.br.SambaWebAPI.exceptions.GroupCreationException;
import com.br.SambaWebAPI.utils.enums.UserManagent.GroupCreationErrorCode;

public class GroupCreationFactory {
    public static GroupCreationException createException(int exitCode) throws  GroupCreationException {
        switch (exitCode) {
            case 4:
                throw new GroupCreationException(GroupCreationErrorCode.GID_ALREADY_EXISTS);
            case 9:
                throw new GroupCreationException(GroupCreationErrorCode.GROUP_NAME_NOT_UNIQUE);
            case 10:
                throw new GroupCreationException(GroupCreationErrorCode.CANT_UPDT_GROUP_FILE);
            default:
                throw new GroupCreationException(GroupCreationErrorCode.GENERIC_ERROR);
        }
    }
}
