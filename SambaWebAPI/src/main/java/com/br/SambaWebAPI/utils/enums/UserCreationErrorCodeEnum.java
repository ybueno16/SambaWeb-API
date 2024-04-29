package com.br.SambaWebAPI.utils.enums;

import org.springframework.http.HttpStatus;

public enum UserCreationErrorCodeEnum {
    GENERIC_ERROR(1, "Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário.", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_ALREADY_EXISTS(2, "O nome de usuário já existe. Você está tentando criar um usuário com um nome que já existe no sistema.", HttpStatus.CONFLICT),
    UID_ALREADY_IN_USE(3, "O UID (User ID) especificado já está em uso. Você está tentando criar um usuário com um UID que já está sendo utilizado por outro usuário.", HttpStatus.CONFLICT),
    GROUP_DOES_NOT_EXIST(4, "O grupo especificado não existe. Você está tentando criar um usuário com um grupo que não existe no sistema.", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    UserCreationErrorCodeEnum(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.errorMessage = message;
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