package com.br.SambaWebAPI.config.ResponseEntity;

public class DefaultResponseEntity {
  protected final String message;
  protected final Object dados;

  public DefaultResponseEntity(String message, Object dados) {
    this.message = message;
    this.dados = dados;
  }

  public String getMessage() {
    return message;
  }

  public Object getData() {
    return dados;
  }
}
