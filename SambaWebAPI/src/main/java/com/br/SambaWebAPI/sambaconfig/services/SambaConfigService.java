package com.br.SambaWebAPI.sambaconfig.services;


import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.sambaconfig.models.SambaConfig;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;

@Service
public class SambaConfigService {
    public void sambaConfigWriteNewConfig(SambaConfig sambaConfig) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(Global.SMB_CONF_PATH,true));
        BufferedReader reader = new BufferedReader(new FileReader(Global.SMB_CONF_PATH));
        try {
            String line;

            while ((line = reader.readLine()) != null) {
                if(line.contains("[" + sambaConfig.getSection() + "]")){
                    System.out.println(line);
                    for (String sectionParam : sambaConfig.getSectionParams()) {
                        writer.newLine();
                        writer.write(Arrays.toString((sectionParam + "\n").toCharArray()));
                    }
                } else {
                    System.out.println(line);
                    writer.newLine();
                    writer.write("[" + sambaConfig.getSection() + "]" + "\n");
                    for (String sectionParam : sambaConfig.getSectionParams()) {
                        writer.write(Arrays.toString((sectionParam + "\n").toCharArray()));
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

