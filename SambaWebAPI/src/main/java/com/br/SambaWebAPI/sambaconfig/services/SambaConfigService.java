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
        BufferedWriter writer = new BufferedWriter(new FileWriter(Global.SMB_CONF_PATH, true));
        BufferedReader reader = new BufferedReader(new FileReader(Global.SMB_CONF_PATH));
        boolean sectionExists = false;

        try {
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

            reader.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sambaConfigEditConfig(
            SambaConfig sambaConfig,
            SudoAuthentication sudoAuthentication)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(Global.SMB_CONF_PATH, true));
        BufferedReader reader = new BufferedReader(new FileReader(Global.SMB_CONF_PATH));
        StringBuffer inputBuffer = new StringBuffer();
        String inputStr = inputBuffer.toString();
        boolean sectionExists = false;
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[" + sambaConfig.getSection() + "]")) {
                    sectionExists = true;
                    if(sambaConfig.getSectionParams()!= null &&!sambaConfig.getSectionParams().isEmpty()){
                        for (String sectionParam : sambaConfig.getSectionParams()) {
                            if(line.startsWith(sambaConfig.getSectionParams() + " " + "=")){
//                                inputStr = inputStr.toLowerCase().replace()
                            }
                        }
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
        }

    }

}
