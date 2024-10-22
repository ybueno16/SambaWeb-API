package com.br.SambaWebAPI.serverconfig.repository;


import com.br.SambaWebAPI.serverconfig.models.ServerConfig;

public interface ServerConfigRepository {
    public void saveServerConfig(ServerConfig serverConfig) throws Exception;
}