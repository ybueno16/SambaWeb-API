package com.br.SambaWebAPI.group.enums;

import com.br.SambaWebAPI.utils.ErrorCode;
import org.springframework.http.HttpStatus;

public class GroupDeleteErrorCode extends ErrorCode {
  public static final GroupDeleteErrorCode GENERIC_ERROR =
      new GroupDeleteErrorCode(
          "Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário.",
          HttpStatus.BAD_REQUEST);
  public static final GroupDeleteErrorCode GROUP_DOESNT_EXIST =
      new GroupDeleteErrorCode("O grupo especificado não existe.", HttpStatus.BAD_REQUEST);
  public static final GroupDeleteErrorCode CANT_REMOVE_PRIMARY_GROUP =
      new GroupDeleteErrorCode(
          "Não foi possível excluir o grupo primário.", HttpStatus.INTERNAL_SERVER_ERROR);
  public static final GroupDeleteErrorCode CANT_UPDT_GROUP_FILE =
      new GroupDeleteErrorCode(
          "Não foi possível atualizar o arquivo de grupos.", HttpStatus.BAD_REQUEST);

  protected GroupDeleteErrorCode(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage, httpStatus);
  }
}
