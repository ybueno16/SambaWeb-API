package com.br.SambaWebAPI.utils.enums.UserManagent;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserCreationErrorCode extends ErrorCode {
    public static final UserCreationErrorCode GENERIC_ERROR = new UserCreationErrorCode(1, "Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário.", HttpStatus.INTERNAL_SERVER_ERROR);
    public static final UserCreationErrorCode DIR_CANT_BE_CREATED = new UserCreationErrorCode(3, "O diretório home do usuário não pode ser criado, possivelmente devido a permissões insuficientes ou falta de espaço em disco.", HttpStatus.BAD_REQUEST);
    public static final UserCreationErrorCode GROUP_DOES_NOT_EXIST = new UserCreationErrorCode(4, "O grupo especificado não existe. Você está tentando criar um usuário com um grupo que não existe no sistema.", HttpStatus.BAD_REQUEST);
    public static final UserCreationErrorCode USR_CANT_BE_CREATED = new UserCreationErrorCode(5, "O usuário não pode ser criado no sistema, possivelmente devido a um erro interno do sistema ou falta de recursos.", HttpStatus.INTERNAL_SERVER_ERROR);
    public static final UserCreationErrorCode CANT_UPDT_PASSWD = new UserCreationErrorCode(10, "O arquivo de senha não pode ser atualizado, possivelmente devido a permissões insuficientes ou um erro de sistema.", HttpStatus.BAD_REQUEST);


    private UserCreationErrorCode(int code, String errorMessage, HttpStatus httpStatus) {
        super(code, errorMessage, httpStatus);
    }
}
