package com.br.SambaWebAPI.user.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserDeleteErrorCode extends ErrorCode {
  public static final UserDeleteErrorCode CANT_UPDT_PASSWD_FILE =
      new UserDeleteErrorCode(
          "Não foi possível atualizar o arquivo de senha.", HttpStatus.INTERNAL_SERVER_ERROR);
  public static final UserDeleteErrorCode USER_DOESNT_EXIST =
      new UserDeleteErrorCode("O usuário especificado não existe.", HttpStatus.CONFLICT);
  public static final UserDeleteErrorCode USER_LOGGED =
      new UserDeleteErrorCode("O usuário está logado.", HttpStatus.CONFLICT);
  public static final UserDeleteErrorCode CANT_UPDT_GROUP_FILE =
      new UserDeleteErrorCode(
          "Não foi possível atualizar o arquivo de grupo.", HttpStatus.INTERNAL_SERVER_ERROR);
  public static final UserDeleteErrorCode CANT_REMOVE_HOME_DIR =
      new UserDeleteErrorCode(
          "Não foi possível excluir a pasta home.", HttpStatus.INTERNAL_SERVER_ERROR);

  private UserDeleteErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
