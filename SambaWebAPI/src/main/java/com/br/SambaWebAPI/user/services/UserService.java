package com.br.SambaWebAPI.user.services;

import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.exceptions.UserCreationException;
import com.br.SambaWebAPI.user.factory.UserCreationFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class UserService {
    private final ProcessBuilder processBuilder;

    public UserService() {
        processBuilder = new ProcessBuilder();
    }



    public boolean createUser(User user, SudoAuthentication sudoAuthentication) throws InterruptedException, IOException, UserCreationException, UserCreationFactory {

        processBuilder.command("sudo", "-S", "useradd", "-m", user.getUser());
        processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        OutputStream outputStream = process.getOutputStream();
        outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
        outputStream.flush();
        outputStream.close();
        process.waitFor();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw UserCreationFactory.createException(exitCode);
        }

        return true;
    }


}
