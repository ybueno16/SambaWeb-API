package com.br.SambaWebAPI.permission.models;

public class OwnerPermission {
  private Integer write;
  private Integer read;
  private Integer execute;

  public Integer getWrite() {
    return write;
  }

  public void setWrite(Integer write) {
    this.write = write;
  }

  public Integer getRead() {
    return read;
  }

  public void setRead(Integer read) {
    this.read = read;
  }

  public Integer getExecute() {
    return execute;
  }

  public void setExecute(Integer execute) {
    this.execute = execute;
  }
}
