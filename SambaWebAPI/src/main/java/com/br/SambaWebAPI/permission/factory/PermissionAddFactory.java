package com.br.SambaWebAPI.permission.factory;

import com.br.SambaWebAPI.permission.enums.PermissionAddErrorCode;
import com.br.SambaWebAPI.permission.exceptions.PermissionAddException;

public class PermissionAddFactory {
  public static PermissionAddException createException() throws PermissionAddException {
    throw new PermissionAddException(PermissionAddErrorCode.GENERIC_ERROR);
  }
}
