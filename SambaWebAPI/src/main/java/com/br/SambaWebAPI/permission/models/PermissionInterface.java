package com.br.SambaWebAPI.permission.models;

public interface PermissionInterface {
    Integer getWrite();
    void setWrite(Integer write);
    Integer getRead();
    void setRead(Integer read);
    Integer getExecute();
    void setExecute(Integer execute);
}
