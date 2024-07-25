package com.br.SambaWebAPI.permission.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class PermissionAddErrorCode extends ErrorCode {

    public static final PermissionAddErrorCode GENERIC_ERROR = new PermissionAddErrorCode(
            "Erro genérico. Ocorreu um erro desconhecido durante a criação da senha do usuario.",
            HttpStatus.BAD_REQUEST);

    private PermissionAddErrorCode(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage, httpStatus);
    }
}


