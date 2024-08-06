package com.br.SambaWebAPI.utils;

import org.springframework.http.HttpStatus;

public abstract class ErrorCode {
  private final String errorMessage;
  private final HttpStatus httpStatus;

  protected ErrorCode(String errorMessage, HttpStatus httpStatus) {
    this.errorMessage = errorMessage;
    this.httpStatus = httpStatus;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
