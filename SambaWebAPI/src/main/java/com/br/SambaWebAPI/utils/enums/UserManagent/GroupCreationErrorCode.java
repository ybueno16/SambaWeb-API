package com.br.SambaWebAPI.utils.enums.UserManagent;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class GroupCreationErrorCode extends ErrorCode {
    public static final GroupCreationErrorCode GENERIC_ERROR = new GroupCreationErrorCode(1, "Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário.", HttpStatus.BAD_REQUEST);
    public static final GroupCreationErrorCode GROUP_ALREADY_EXISTS = new GroupCreationErrorCode(2, "Grupo já existe. O grupo que você está tentando criar já existe.", HttpStatus.BAD_REQUEST);
    public static final GroupCreationErrorCode INVALID_NAME_GROUP = new GroupCreationErrorCode(3, "Nome de grupo inválido. O nome do grupo não atende aos padrões de nomeação do sistema.", HttpStatus.BAD_REQUEST);
    public static final GroupCreationErrorCode SYSTEM_ERROR = new GroupCreationErrorCode(5, "Erro de sistema. Um erro de sistema ocorreu, como falta de memória ou problemas de disco.", HttpStatus.INTERNAL_SERVER_ERROR);
    public static final GroupCreationErrorCode PERMISSION_DENIED = new GroupCreationErrorCode(6, "Permissão negada. Você não tem permissão para criar grupos.", HttpStatus.BAD_REQUEST);

    private GroupCreationErrorCode(int code, String errorMessage, HttpStatus httpStatus) {
        super(code, errorMessage, httpStatus);
    }
}
