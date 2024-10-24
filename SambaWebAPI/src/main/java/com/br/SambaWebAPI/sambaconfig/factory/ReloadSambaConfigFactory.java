package com.br.SambaWebAPI.sambaconfig.factory;

import com.br.SambaWebAPI.sambaconfig.enums.ReloadSambaConfigErrorCode;
import com.br.SambaWebAPI.sambaconfig.exceptions.ReloadSambaConfigException;

public class ReloadSambaConfigFactory extends Throwable {
  public static ReloadSambaConfigException createException(int exitCode)
      throws ReloadSambaConfigException {
    throw new ReloadSambaConfigException(ReloadSambaConfigErrorCode.GENERIC_ERROR);
  }
}
