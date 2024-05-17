package com.br.SambaWebAPI.sambaconfig.controllers;

import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.sambaconfig.models.SambaConfig;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

import static com.br.SambaWebAPI.config.Global.API_URL_SAMBA;

@RestController
@RequestMapping(API_URL_SAMBA + "/samba-share-config")
public class SambaShareConfigController {
    SambaConfig sambaConfig = new SambaConfig();
    private static final String SMB_CONF_FILE = "smb.conf";
    @PostMapping(path = "/global/{usuario}/{senha}")
    public void InitialSambaShare(@PathVariable String usuario, @PathVariable String senha) throws IOException {

    }
}
