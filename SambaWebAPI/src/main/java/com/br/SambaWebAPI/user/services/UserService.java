package com.br.SambaWebAPI.user.services;

import com.br.SambaWebAPI.adapter.impl.ProcessBuilderAdapterImpl;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.user.exceptions.UserCreationException;
import com.br.SambaWebAPI.user.factory.UserCreationFactory;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.utils.CommandConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    private ProcessBuilderAdapter processBuilderAdapter;

    @Autowired
    public UserService(ProcessBuilderAdapter processBuilderAdapter){
        this.processBuilderAdapter = processBuilderAdapter;
    }

    public boolean createUser(User user, SudoAuthentication sudoAuthentication) throws Exception {
        processBuilderAdapter = new ProcessBuilderAdapterImpl();

        processBuilderAdapter.command("exit");

        ProcessBuilder processBuilder = processBuilderAdapter.command(
                "bash",
                "-c",
                "echo \"" + sudoAuthentication.getSudoPassword() + "\" | sudo -S useradd " + user.getUser()
        ).redirectInput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw UserCreationFactory.createException(exitCode);
        }

        return true;
    }

    public boolean removeUser(User user, SudoAuthentication sudoAuthentication) throws Exception {
        ProcessBuilder processBuilder = processBuilderAdapter.command(
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.USER_DEL,
                user.getUser()
        ).redirectInput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        OutputStream outputStream = process.getOutputStream();

        outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
        outputStream.flush();
        outputStream.close();
        process.waitFor();

        int exitCode = process.waitFor();
        return exitCode == 0;
    }

    public boolean getUser(User user) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "cat /etc/passwd | grep " + user.getUser());
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        reader.read();
        reader.close();

        int exitCode = process.waitFor();
        return exitCode == 0;
    }
}