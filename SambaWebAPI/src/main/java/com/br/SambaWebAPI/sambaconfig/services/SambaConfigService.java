package com.br.SambaWebAPI.sambaconfig.services;


import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.sambaconfig.models.SambaConfig;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;

@Service
public class SambaConfigService {
    public void sambaConfigWriteNewConfig(SambaConfig sambaConfig, SudoAuthentication sudoAuthentication) throws IOException {
        System.out.println("Abrindo arquivo: " + Global.SMB_CONF_PATH);
        BufferedWriter writer = new BufferedWriter(new FileWriter(Global.SMB_CONF_PATH, true));
        BufferedReader reader = new BufferedReader(new FileReader(Global.SMB_CONF_PATH));
        boolean sectionExists = false;

        try {
            String line;
            while ((line = reader.readLine())!= null) {
                if (line.startsWith("[" + sambaConfig.getSection() + "]")) {
                    sectionExists = true;
                    for (String sectionParam : sambaConfig.getSectionParams()) {
                        writer.write(sectionParam + "\n");
                    }
                }
            }

            if (!sectionExists) {
                writer.write("\n[" + sambaConfig.getSection() + "]\n");
                for (String sectionParam : sambaConfig.getSectionParams()) {
                    writer.write(sectionParam + "\n");
                }
            }

            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}

