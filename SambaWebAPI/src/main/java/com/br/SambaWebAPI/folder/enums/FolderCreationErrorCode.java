package com.br.SambaWebAPI.folder.enums;

import com.br.SambaWebAPI.user.enums.UserCreationErrorCode;
import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class FolderCreationErrorCode extends ErrorCode {
    public static final FolderCreationErrorCode CANT_UPDT_PASSWD_FILE = new FolderCreationErrorCode(
            "Não foi possível atualizar o arquivo de senha.", HttpStatus.BAD_REQUEST);

    public static final FolderCreationErrorCode USR_ALREADY_EXISTS = new FolderCreationErrorCode(
            "O usuario que está tentando criar já existe", HttpStatus.CONFLICT);

    public static final FolderCreationErrorCode CANT_CREATE_HOME_DIR = new FolderCreationErrorCode(
            "Não foi possível criar a pasta home", HttpStatus.INTERNAL_SERVER_ERROR);

    public static final FolderCreationErrorCode CANT_CREATE_MAIL_SPOOL = new FolderCreationErrorCode(
            "Não foi possível criar a poasta de correio.", HttpStatus.INTERNAL_SERVER_ERROR);

    public static final FolderCreationErrorCode CANT_UPDATE_SELINUX = new FolderCreationErrorCode(
            "Não foi possível atualizar o mapeamento de usuários do SELinux", HttpStatus.BAD_REQUEST);

    public static final FolderCreationErrorCode GENERIC_ERROR = new FolderCreationErrorCode(
            "Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário.",
            HttpStatus.INTERNAL_SERVER_ERROR);
    private FolderCreationErrorCode( String errorMessage, HttpStatus httpStatus) {
        super(errorMessage, httpStatus);
    }

}
