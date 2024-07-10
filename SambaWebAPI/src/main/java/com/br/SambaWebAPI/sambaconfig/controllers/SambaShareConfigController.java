package com.br.SambaWebAPI.sambaconfig.controllers;

import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.sambaconfig.models.SambaConfig;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;


@RestController
@RequestMapping("${api.samba.baseurl}/samba-share-config")
@PropertySource("classpath:application.properties")

public class SambaShareConfigController {
    SambaConfig sambaConfig = new SambaConfig();
    private static final String SMB_CONF_FILE = "smb.conf";
    @PostMapping(path = "/global/{usuario}/{senha}")
    public void InitialSambaShare(@PathVariable String usuario, @PathVariable String senha) throws IOException {

    }
}
