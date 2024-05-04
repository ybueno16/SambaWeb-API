package com.br.SambaWebAPI.LinuxInitialConfig.controllers;

import com.br.SambaWebAPI.LinuxInitialConfig.models.Group;
import com.br.SambaWebAPI.LinuxInitialConfig.models.SudoAuthentication;
import com.br.SambaWebAPI.LinuxInitialConfig.models.User;
import com.br.SambaWebAPI.LinuxInitialConfig.services.DefaultUserConfigurationService;
import com.br.SambaWebAPI.config.ResponseEntity.DefaultResponseEntityFactory;
import com.br.SambaWebAPI.exceptions.GroupCreationException;
import com.br.SambaWebAPI.exceptions.UserCreationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.br.SambaWebAPI.config.Global.API_URL_SAMBA;

@RestController
@RequestMapping(API_URL_SAMBA + "/user-config")
public class DefaultUserConfigurationController {

    ObjectMapper objectMapper = new ObjectMapper();
    DefaultUserConfigurationService defaultUserConfigurationService = new DefaultUserConfigurationService();

    @PostMapping(path = "/cadastro")
    public ResponseEntity<?> InitialUserCreation(@RequestBody Map<String, Object> json) {
        User user = objectMapper.convertValue(json.get("user"), User.class);
        SudoAuthentication sudoAuthentication = objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
        try {
            defaultUserConfigurationService.registerUser(user,sudoAuthentication);
            defaultUserConfigurationService.registerPassword(user);

            return DefaultResponseEntityFactory.create("Usuario criado com sucesso!", user, HttpStatus.OK);
        } catch (UserCreationException e) {
            return DefaultResponseEntityFactory.create(e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            return DefaultResponseEntityFactory.create("Erro genérico. Ocorreu um erro desconhecido durante a criação do usuário.", null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/cadastro-grupo")
    public ResponseEntity<?> InitialGroupCreation(@RequestBody Map<String, Object> json) {
//        User user = objectMapper.convertValue(json.get("user"), User.class);
        Group group = objectMapper.convertValue(json.get("group"), Group.class);
        SudoAuthentication sudoAuthentication = objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
        try {
            defaultUserConfigurationService.registerGroup(group,sudoAuthentication);

            return DefaultResponseEntityFactory.create("Grupo criado com sucesso!", group, HttpStatus.OK);
        } catch (GroupCreationException e) {
            return DefaultResponseEntityFactory.create(e.getErrorCode().getErrorMessage(), null, e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            return DefaultResponseEntityFactory.create("Erro genérico. Ocorreu um erro desconhecido durante a criação do grupo.", null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
