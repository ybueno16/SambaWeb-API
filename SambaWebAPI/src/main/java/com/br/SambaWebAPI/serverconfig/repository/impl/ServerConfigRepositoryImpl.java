package com.br.SambaWebAPI.serverconfig.repository.impl;

import com.br.SambaWebAPI.serverconfig.models.ServerConfig;
import com.br.SambaWebAPI.serverconfig.repository.ServerConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

@Repository
public class ServerConfigRepositoryImpl implements ServerConfigRepository {

    StringBuilder sql = null;
    private final DataSource dataSource;

    @Autowired
    public ServerConfigRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveServerConfig(ServerConfig serverConfig) throws Exception {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO public.server_config \n")
                    .append("(ip, port) \n")
                    .append("VALUES(")
                    .append("'")
                    .append(serverConfig.getIp())
                    .append("'")
                    .append(", ")
                    .append(serverConfig.getPort())
                    .append(")");
            stmt.execute(sql.toString());
        } catch (SQLException e) {
            throw new SQLException("There was an error when inserting into the database.");
        }
    }
}