package com.br.SambaWebAPI.UbuntuConfig.services;

import com.br.SambaWebAPI.UbuntuConfig.models.User;

import java.io.IOException;
import java.io.OutputStream;

public class UserConfigurationService {

    public boolean cadastrarUsuario(User user) throws Exception {

        ProcessBuilder processBuilder = new ProcessBuilder("sudo", "-S", "useradd", "-m", user.getUser());
        processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        OutputStream outputStream = process.getOutputStream();
        outputStream.write((user.getSenhaSudo() + "\n").getBytes());
        outputStream.flush();
        outputStream.close();

        process.waitFor();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new Exception("Erro ao criar usuário: " + user.getUser() + "Com código de erro " + exitCode);
        }



        return true;
    }
}