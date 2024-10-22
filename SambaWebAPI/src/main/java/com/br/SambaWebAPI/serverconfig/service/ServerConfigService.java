package com.br.SambaWebAPI.serverconfig.service;

import com.br.SambaWebAPI.serverconfig.models.ServerConfig;
import com.br.SambaWebAPI.serverconfig.repository.ServerConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ServerConfigService {

    private final ServerConfigRepository serverConfigRepository;

    @Autowired
    public ServerConfigService(ServerConfigRepository serverConfigRepository) {
        this.serverConfigRepository = serverConfigRepository;
    }

    public boolean saveServerConfig(ServerConfig serverConfig) throws Exception {
        try {
            serverConfigRepository.saveServerConfig(serverConfig);
            return true;
        } catch (SQLException e){
            throw new SQLException("Error saving server config.", e.getMessage(), e);
        } catch (Exception e){
            throw new Exception("Error saving server config: " + e.getMessage(), e);
        }
    }
}
