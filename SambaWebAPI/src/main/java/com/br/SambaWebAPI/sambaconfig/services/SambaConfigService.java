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
        BufferedWriter writer = new BufferedWriter(new FileWriter(Global.SMB_CONF_PATH,true));
        BufferedReader reader = new BufferedReader(new FileReader(Global.SMB_CONF_PATH));
        try {
            String line;

            while ((line = reader.readLine()) != null) {

                boolean comeca = line.startsWith(";" + sambaConfig.getSection() + "]");
                boolean comecaDiff = line.startsWith(";" +"[" + sambaConfig.getSection() + "]");
                if(comeca || comecaDiff){
                    for (String sectionParam : sambaConfig.getSectionParams()) {
                        writer.write(sectionParam + "\n");
//                        System.out.println(line);
                        System.out.println("section:"+sambaConfig.getSection());
                        System.out.println("sectionParams:"+sambaConfig.getSectionParams());

                    }
                } else {
                    writer.write("[" + sambaConfig.getSection() + "]" + "\n");
                    for (String sectionParam : sambaConfig.getSectionParams()) {
                        writer.write(sectionParam + "\n");
//                        System.out.println("sectionInvalid:"+sambaConfig.getSection());
//                        System.out.println("sectionParamsInvalid:"+sambaConfig.getSectionParams());;
//                        System.out.println(line);


                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

