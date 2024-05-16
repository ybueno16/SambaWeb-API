package com.br.SambaWebAPI.group.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class GroupCreationErrorCode extends ErrorCode {
    public static final GroupCreationErrorCode GENERIC_ERROR = new GroupCreationErrorCode("Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário.", HttpStatus.BAD_REQUEST);
    public static final GroupCreationErrorCode GID_ALREADY_EXISTS = new GroupCreationErrorCode("Group ID já existe.", HttpStatus.INTERNAL_SERVER_ERROR);
    public static final GroupCreationErrorCode GROUP_NAME_NOT_UNIQUE = new GroupCreationErrorCode("O nome do grupo já existe.", HttpStatus.BAD_REQUEST);
    public static final GroupCreationErrorCode CANT_UPDT_GROUP_FILE = new GroupCreationErrorCode("Não foi possível atualizar o arquivo de configuração de grupos.", HttpStatus.INTERNAL_SERVER_ERROR);



    private GroupCreationErrorCode(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage, httpStatus);
    }
}
