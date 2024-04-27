package com.br.SambaWebAPI.UbuntuConfig.services;


import com.br.SambaWebAPI.UbuntuConfig.models.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;


public class UserConfigurationService {

    public boolean cadastrarUsuario(User user) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("sudo", "-S", "useradd ", "-n ", user.getUser());

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        OutputStream outputStream = process.getOutputStream();
        outputStream.write(user.getSenhaSudo().getBytes());
        outputStream.flush();
        outputStream.close();


        int exitCode = process.waitFor();
        if (exitCode!= 0) {
            throw new Exception("Erro ao criar usuário: " + user.getUser() + " " + exitCode);
        }

        processBuilder = new ProcessBuilder("sudo", "-S", "passwd", user.getUser());
        processBuilder.redirectErrorStream(true);

        process = processBuilder.start();

        outputStream = process.getOutputStream();
        outputStream.write(user.getSenhaSudo().getBytes());
        outputStream.flush();
        outputStream.close();

        outputStream.write((user.getPassword() + "\n" + user.getPassword() + "\n").getBytes());
        outputStream.flush();
        outputStream.close();

        exitCode = process.waitFor();
        if (exitCode!= 0) {
            throw new Exception("Erro ao setar senha para o usuário: " + user.getUser());
        }

        return true;
    }

}
