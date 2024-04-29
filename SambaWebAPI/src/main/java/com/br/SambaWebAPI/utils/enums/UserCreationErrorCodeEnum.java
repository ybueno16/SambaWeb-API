package com.br.SambaWebAPI.utils.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCodeEnum {
    GENERIC_ERROR(1, HttpStatus.INTERNAL_SERVER_ERROR, "Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário."),
    USERNAME_ALREADY_EXISTS(2, HttpStatus.CONFLICT, "O nome de usuário já existe. Você está tentando criar um usuário com um nome que já existe no sistema."),
    UID_ALREADY_IN_USE(3, HttpStatus.CONFLICT, "O UID (User ID) especificado já está em uso. Você está tentando criar um usuário com um UID que já está sendo utilizado por outro usuário."),
    GROUP_DOES_NOT_EXIST(4, HttpStatus.BAD_REQUEST, "O grupo especificado não existe. Você está tentando criar um usuário com um grupo que não existe no sistema.");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCodeEnum(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}