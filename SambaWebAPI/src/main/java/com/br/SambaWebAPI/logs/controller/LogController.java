package com.br.SambaWebAPI.logs.controller;


import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.logs.models.Log;
import com.br.SambaWebAPI.logs.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(Global.API_URL_SAMBA + "/log")
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public ResponseEntity<?> writeSambaFile(@RequestBody Log log ) throws Exception {
        try {
            boolean retorno = logService.insertLog(log);
            return DefaultResponseEntityFactory.create("Log foi inserido com sucesso.", retorno, HttpStatus.OK);
        } catch (Exception e) {

            return DefaultResponseEntityFactory.create("Erro ao salvarlogs.", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}