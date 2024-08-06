package com.br.SambaWebAPI.config.ResponseEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DefaultResponseEntityFactory {
  public static ResponseEntity<DefaultResponseEntity> create(
      String pMessage, Object pObject, HttpStatus pHttpStatus) {
    System.out.println(pMessage);

    return new ResponseEntity<DefaultResponseEntity>(
        new DefaultResponseEntity(pMessage, pObject), pHttpStatus);
  }
}
