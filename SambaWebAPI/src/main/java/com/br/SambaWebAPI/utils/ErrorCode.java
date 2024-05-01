package com.br.SambaWebAPI.utils;

import org.springframework.http.HttpStatus;

public abstract class ErrorCode {
    private final int code;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    protected ErrorCode(int code, String errorMessage, HttpStatus httpStatus) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}