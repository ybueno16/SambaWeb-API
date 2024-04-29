package com.br.SambaWebAPI.UbuntuConfig.controllers;

import com.br.SambaWebAPI.UbuntuConfig.models.User;
import com.br.SambaWebAPI.UbuntuConfig.services.UserConfigurationService;
import com.br.SambaWebAPI.SambaConfig.models.SambaConfig;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.br.SambaWebAPI.config.Global.API_URL_SAMBA;

@RestController
@RequestMapping(API_URL_SAMBA + "/user-config")
public class UserConfigurationController {
    SambaConfig sambaConfig = new SambaConfig();
    UserConfigurationService userConfigurationService = new UserConfigurationService();
    User user = new User();

    @PostMapping(path = "/cadastro")
    public void InitialSambaShare(@RequestBody User user) throws IOException {

        try {
            userConfigurationService.cadastrarUsuario(user);
            userConfigurationService.cadastrarSenha(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
