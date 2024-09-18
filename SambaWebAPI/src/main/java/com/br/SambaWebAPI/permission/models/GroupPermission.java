package com.br.SambaWebAPI.permission.models;

public class GroupPermission implements PermissionInterface {
  private Integer write;
  private Integer read;
  private Integer execute;

  @Override
  public Integer getWrite() {
    return write;
  }

  @Override
  public void setWrite(Integer write) {
    this.write = write;
  }

  @Override
  public Integer getRead() {
    return read;
  }

  @Override
  public void setRead(Integer read) {
    this.read = read;
  }

  @Override
  public Integer getExecute() {
    return execute;
  }

  @Override
  public void setExecute(Integer execute) {
    this.execute = execute;
  }

}
