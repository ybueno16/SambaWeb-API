package com.br.SambaWebAPI.LinuxInitialConfig.controllers;

import com.br.SambaWebAPI.LinuxInitialConfig.models.SudoAuthentication;
import com.br.SambaWebAPI.LinuxInitialConfig.models.User;
import com.br.SambaWebAPI.LinuxInitialConfig.services.DefaultUserConfigurationService;
import com.br.SambaWebAPI.SambaConfig.models.SambaConfig;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.exceptions.UserCreationExceptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.br.SambaWebAPI.config.Global.API_URL_SAMBA;

@RestController
@RequestMapping(API_URL_SAMBA + "/user-config")
public class DefaultUserConfigurationController {
    SambaConfig sambaConfig = new SambaConfig();
    DefaultUserConfigurationService userConfigurationService = new DefaultUserConfigurationService();
    User user = new User();

    @PostMapping(path = "/cadastro")
    public ResponseEntity<?> InitialSambaShare(@RequestBody Map<String, Object> json) {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.convertValue(json.get("user"), User.class);
        SudoAuthentication sudoAuthentication = objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
        try {
            userConfigurationService.cadastrarUsuario(user,sudoAuthentication);
            userConfigurationService.cadastrarSenha(user);

            return DefaultResponseEntityFactory.create("Usuario criado com sucesso!", null, HttpStatus.OK);
        } catch (UserCreationExceptions e) {
            return DefaultResponseEntityFactory.create(e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            return DefaultResponseEntityFactory.create("Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário.", null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
