package com.br.SambaWebAPI.sambaconfig.exceptions;

import com.br.SambaWebAPI.sambaconfig.enums.ReloadSambaConfigErrorCode;

public class ReloadSambaConfigException extends Exception {
  private final ReloadSambaConfigErrorCode errorCode;

  public ReloadSambaConfigException(ReloadSambaConfigErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public ReloadSambaConfigErrorCode getErrorCode() {
    return errorCode;
  }
}
