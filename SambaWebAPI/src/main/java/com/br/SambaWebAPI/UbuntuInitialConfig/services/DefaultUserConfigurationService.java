package com.br.SambaWebAPI.UbuntuInitialConfig.services;

import com.br.SambaWebAPI.UbuntuInitialConfig.models.User;
import com.br.SambaWebAPI.exceptions.UserCreationExceptions;
import com.br.SambaWebAPI.utils.enums.UserCreationErrorCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;


@Service
public class DefaultUserConfigurationService {

    private final ProcessBuilder processBuilder;


    public DefaultUserConfigurationService() {
        processBuilder = new ProcessBuilder();
    }

    public boolean cadastrarUsuario(User user) throws UserCreationExceptions, InterruptedException, IOException {

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
            switch (exitCode) {
                case 1:
                    throw new UserCreationExceptions(UserCreationErrorCodeEnum.GENERIC_ERROR);
                case 2:
                    throw new UserCreationExceptions(UserCreationErrorCodeEnum.GROUP_DOES_NOT_EXIST);
                default:
                    throw new UserCreationExceptions(UserCreationErrorCodeEnum.USERNAME_ALREADY_EXISTS);
            }
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