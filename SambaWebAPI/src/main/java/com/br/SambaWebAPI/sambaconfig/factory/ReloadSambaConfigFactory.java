package com.br.SambaWebAPI.sambaconfig.factory;

import com.br.SambaWebAPI.sambaconfig.enums.ReloadSambaConfigErrorCode;
import com.br.SambaWebAPI.sambaconfig.exceptions.ReloadSambaConfigException;
import com.br.SambaWebAPI.user.enums.DeleteUserSambaErrorCode;
import com.br.SambaWebAPI.user.exceptions.UserSambaDeleteException;

public class ReloadSambaConfigFactory extends Throwable {
    public static ReloadSambaConfigException createException(int exitCode)
            throws ReloadSambaConfigException {
        throw new ReloadSambaConfigException(ReloadSambaConfigErrorCode.GENERIC_ERROR);
    }
}
