package com.br.SambaWebAPI.sambaconfig.services;


import com.br.SambaWebAPI.config.Global;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class SambaConfigService {
    public void sambaConfigReader(String section) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(Global.SMB_CONF_PATH));
        BufferedReader reader = new BufferedReader(new FileReader(Global.SMB_CONF_PATH));
        try {
            String line;

            while ((line = reader.readLine()) != null) {
                if(line.contains("[" + section + "]")){
                    System.out.println(line);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

