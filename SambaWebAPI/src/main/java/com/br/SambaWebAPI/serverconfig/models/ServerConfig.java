package com.br.SambaWebAPI.serverconfig.models;

public class ServerConfig {
  private Long id;
  private String ip;
  private Integer port;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }
}
