package com.br.SambaWebAPI.sambaconfig.controller;

import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.sambaconfig.models.SambaConfig;
import com.br.SambaWebAPI.sambaconfig.services.SambaConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(Global.API_URL_SAMBA +"/samba-config")
public class SambaConfigController {
    final private ObjectMapper objectMapper;
    final private SambaConfigService sambaConfigService;

    @Autowired
    public SambaConfigController(ObjectMapper objectMapper, SambaConfigService sambaConfigService){
        this.objectMapper = objectMapper;
        this.sambaConfigService = sambaConfigService;


    }

    @PostMapping(path = "/configureSambaFile")
    public ResponseEntity<?> configureSambaFile(@RequestBody Map<String,Object> json){
        SambaConfig sambaConfig = objectMapper.convertValue(json.get("sambaConfig"), SambaConfig.class);
        SudoAuthentication sudoAuthentication = objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
        try{
            sambaConfigService.sambaConfigWriteNewConfig(sambaConfig,sudoAuthentication);
            return DefaultResponseEntityFactory.create("Configuração salva com sucesso!", sambaConfig, HttpStatus.OK);


        } catch (IOException e) {
            return DefaultResponseEntityFactory.create(
                    "Ocorreu um erro na escrita do arquivo. " + e,
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            return DefaultResponseEntityFactory.create(
                    "Erro genérico. Ocorreu um erro desconhecido durante a escrita no arquivo." + Global.SMB_CONF_PATH + " " + e,
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
