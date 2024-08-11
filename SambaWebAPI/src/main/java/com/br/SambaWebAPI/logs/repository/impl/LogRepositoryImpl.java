package com.br.SambaWebAPI.logs.repository.impl;

import com.br.SambaWebAPI.logs.models.Log;
import com.br.SambaWebAPI.logs.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

@Repository
public class LogRepositoryImpl implements LogRepository {

    StringBuilder sql = null;
    private final Log log;
    private final DataSource dataSource;

    @Autowired
    public LogRepositoryImpl(Log log, @Qualifier("dataSource") DataSource dataSource) {
        this.log = log;
        this.dataSource = dataSource;
    }

    @Override
    public boolean insertLog(Log log) throws Exception {




        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO public.logs \n")
                .append("(\"date\", \"hour\", log_description) \n")
                .append("VALUES(")
                .append(log.getDate())
                .append(",")
                .append(log.getHour())
                .append(",")
                .append(log.getLogDescription())
               .append(")");
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();){
            stmt.execute(sql.toString());
            return true;
        }

    }

}
