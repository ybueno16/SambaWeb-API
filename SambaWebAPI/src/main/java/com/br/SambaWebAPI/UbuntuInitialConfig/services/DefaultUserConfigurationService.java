package com.br.SambaWebAPI.UbuntuConfig.services;

import com.br.SambaWebAPI.UbuntuConfig.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;


@Service
public class UserConfigurationService {

    private final ProcessBuilder processBuilder;


    public UserConfigurationService() {
        processBuilder = new ProcessBuilder();
    }

    public boolean cadastrarUsuario(User user) throws Exception {

        processBuilder.command("sudo", "-S", "useradd", "-m", user.getUser());
        processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        OutputStream outputStream = process.getOutputStream();
        outputStream.write((user.getSenhaSudo() + "\n").getBytes());
        outputStream.flush();
        outputStream.close();
        process.waitFor();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new Exception("Erro ao criar usuário: " + user.getUser() + " Com código de erro " + exitCode);
        }

        return true;
    }

    public boolean cadastrarSenha(User user) throws Exception{
       processBuilder.command("sudo", "-S", "passwd", user.getUser());
        processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        OutputStream outputStream = process.getOutputStream();
        outputStream.write((user.getSenhaSudo() + "\n").getBytes());
        outputStream.write((user.getPassword() + "\n").getBytes());
        outputStream.write((user.getPassword() + "\n").getBytes());
        outputStream.flush();
        outputStream.close();
        process.waitFor();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new Exception("Erro ao criar usuário: " + user.getUser() + " Com código de erro " + exitCode);
        }

        return true;
    }
}