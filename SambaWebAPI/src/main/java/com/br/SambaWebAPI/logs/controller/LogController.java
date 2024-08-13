package com.br.SambaWebAPI.logs.controller;


import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.logs.models.Log;
import com.br.SambaWebAPI.logs.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;


@RestController
@RequestMapping(Global.API_URL_SAMBA + "/log")
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public ResponseEntity<?> writeSambaFile() throws Exception {
        try {
            boolean retorno = logService.insertLogs();
            return DefaultResponseEntityFactory.create("Log foi inserido com sucesso.", retorno, HttpStatus.OK);
        }catch (SQLException e){
            return DefaultResponseEntityFactory.create("Houve algum erro ao inserir no banco.", null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        catch (Exception e) {
            return DefaultResponseEntityFactory.create("Erro ao salvar logs.", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}