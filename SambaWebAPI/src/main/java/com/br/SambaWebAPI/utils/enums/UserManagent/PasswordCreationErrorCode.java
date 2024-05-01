package com.br.SambaWebAPI.utils.enums.UserManagent;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class PasswordCreationErrorCode extends ErrorCode {
    public static final PasswordCreationErrorCode GENERIC_ERROR = new PasswordCreationErrorCode(1, "Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário.", HttpStatus.INTERNAL_SERVER_ERROR);
    public static final PasswordCreationErrorCode CANT_READ_PASSWD_FILE = new PasswordCreationErrorCode(3, "Falha ao ler o arquivo de senha. O arquivo de senha não pode ser lido.", HttpStatus.INTERNAL_SERVER_ERROR);
    public static final PasswordCreationErrorCode CANT_WRITE_PASSWD_FILE = new PasswordCreationErrorCode(4, "Falha ao gravar o arquivo de senha. O arquivo de senha não pode ser gravado.", HttpStatus.INTERNAL_SERVER_ERROR);
    public static final PasswordCreationErrorCode INVALID_PASSWD = new PasswordCreationErrorCode(5, "Senha inválida. A senha especificada é inválida.", HttpStatus.BAD_REQUEST);
    public static final PasswordCreationErrorCode ERROR_CONFIRMATION_PASSWD = new PasswordCreationErrorCode(6, "Confirmação de senha inválida. A confirmação de senha não coincide com a senha especificada.", HttpStatus.BAD_REQUEST);

    private PasswordCreationErrorCode(int code, String errorMessage, HttpStatus httpStatus) {
        super(code, errorMessage, httpStatus);
    }
}