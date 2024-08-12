package com.br.SambaWebAPI.logs.repository.impl;

import com.br.SambaWebAPI.logs.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Repository
public class LogRepositoryImpl implements LogRepository {

    StringBuilder sql = null;
    private final DataSource dataSource;

    @Autowired
    public LogRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean insertLog(String date, String hour, String logDescription) throws Exception {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO public.logs \n")
                    .append("(\"date\", \"hour\", log_description) \n")
                    .append("VALUES(").append("'").append(date).append("'")
                    .append(", ").append("'").append(hour).append("'")
                    .append(", ").append("'").append(logDescription).append("'")
                    .append(")");
            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement();){
                stmt.execute(sql.toString());
                System.out.println("SQL: " + sql.toString());
                return true;
            }
        } catch (Exception e) {
            System.out.println("Erro ao inserir log: " + e.getMessage());
            throw e;
        }
    }

}
