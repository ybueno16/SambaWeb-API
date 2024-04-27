package com.br.SambaWebAPI.controllers;

import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.models.SambaConfig;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

import static com.br.SambaWebAPI.config.Global.API_URL_SAMBA;

@RestController
@RequestMapping(API_URL_SAMBA + "/samba-share-config/{usuario}/{senha}")
public class SambaShareConfig {
    SambaConfig sambaConfig = new SambaConfig();
    private static final String SMB_CONF_FILE = "smb.conf";
    @PostMapping
    public void InitialSambaShare(@PathVariable String usuario, @PathVariable String senha) throws IOException {
        FileWriter writer = new FileWriter(Global.SMB_CONF_PATH);
        writer.write("workgroup = " + String.join(", ", sambaConfig.getWorkgroups()) + "\n");
        writer.write("server string = " + String.join(", ", sambaConfig.getServerStrings()) + "\n");
        writer.write("security = " + String.join(", ", sambaConfig.getSecurities()) + "\n");
        writer.write("encrypt passwords = " + sambaConfig.getEncryptPasswords() + "\n");
        writer.write("smb passwd file = " + String.join(", ", sambaConfig.getSmbPasswdFiles()) + "\n");
        writer.write("hosts allow = " + String.join(", ", sambaConfig.getHostsAllow()) + "\n");
        writer.write("hosts deny = " + String.join(", ", sambaConfig.getHostsDeny()) + "\n");
        writer.write("interfaces = " + String.join(", ", sambaConfig.getInterfaces()) + "\n");
        writer.write("socket options = " + String.join(", ", sambaConfig.getSocketOptions()) + "\n");
        writer.write("log files = " + String.join(", ", sambaConfig.getLogFiles()) + "\n");
        writer.write("log levels = " + sambaConfig.getLogLevels().stream().map(String::valueOf).collect(Collectors.joining(", ")) + "\n");

        writer.close();
    }
}
