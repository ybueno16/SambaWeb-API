package com.br.SambaWebAPI.password.services;

import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.password.factory.PasswordCreationFactory;

import java.io.OutputStream;

public class PasswordService {
    private final ProcessBuilder processBuilder;

    public PasswordService() {
        processBuilder = new ProcessBuilder();
    }
    public boolean createPassword(User user) throws Exception {
        processBuilder.command("sudo", "-S", "passwd", user.getUser());
        processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        OutputStream outputStream = process.getOutputStream();
        outputStream.write((user.getPassword() + "\n").getBytes());
        outputStream.write((user.getPassword() + "\n").getBytes());
        outputStream.flush();
        outputStream.close();
        process.waitFor();

        int exitCode = process.waitFor();
        if (exitCode!= 0) {
            throw PasswordCreationFactory.createException(exitCode);
        }

        return true;
    }
}
