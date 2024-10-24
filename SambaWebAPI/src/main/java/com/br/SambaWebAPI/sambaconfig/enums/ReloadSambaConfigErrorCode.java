package com.br.SambaWebAPI.sambaconfig.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class ReloadSambaConfigErrorCode extends ErrorCode {
  public static final ReloadSambaConfigErrorCode GENERIC_ERROR =
      new ReloadSambaConfigErrorCode(
          "Generic error. An unknown error occurred while refreshing the samba config.",
          HttpStatus.INTERNAL_SERVER_ERROR);

  private ReloadSambaConfigErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
