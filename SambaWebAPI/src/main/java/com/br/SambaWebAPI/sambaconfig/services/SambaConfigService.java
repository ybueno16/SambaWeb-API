package com.br.SambaWebAPI.sambaconfig.services;


import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.sambaconfig.models.SambaConfig;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class SambaConfigService {
    public void sambaConfigWriteNewConfig(
            SambaConfig sambaConfig,
            SudoAuthentication sudoAuthentication)
            throws IOException {

        boolean sectionExists = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Global.SMB_CONF_PATH, true));
             BufferedReader reader = new BufferedReader(new FileReader(Global.SMB_CONF_PATH))){
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[" + sambaConfig.getSection() + "]")) {
                    sectionExists = true;
                    for (String sectionParam : sambaConfig.getSectionParams()) {
                        writer.write(sectionParam.toLowerCase() + "\n");
                    }
                }
            }

            if (!sectionExists) {
                writer.write("\n[" + sambaConfig.getSection() + "]\n");
                for (String sectionParam : sambaConfig.getSectionParams()) {
                    writer.write(sectionParam.toLowerCase() + "\n");
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sambaConfigEditConfig(
            SambaConfig sambaConfig,
            SudoAuthentication sudoAuthentication)
            throws IOException {

        boolean sectionExists = false;
        StringBuilder modifiedContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(Global.SMB_CONF_PATH))) {

            String line;

            while ((line = reader.readLine())!= null) {
                if (line.startsWith("[" + sambaConfig.getSection() + "]")) {
                    sectionExists = true;
                    for (String sectionParam : sambaConfig.getSectionParams()) {
                        String[] parts = sectionParam.split("=");
                        String key = parts[0].trim();
                        String value = parts[1].trim();
                        if (line.contains(key + " = ")) {
                            line = line.replace(key + " = " + line.split(key + " = ")[1], key + " = " + value);
                        }
                    }
                }
                modifiedContent.append(line).append("\n");
            }
        }

        try (FileWriter writer = new FileWriter(Global.SMB_CONF_PATH)) {
            writer.write(modifiedContent.toString());
        }
    }
}
