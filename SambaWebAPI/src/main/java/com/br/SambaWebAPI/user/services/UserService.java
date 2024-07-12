package com.br.SambaWebAPI.user.services;

import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.user.factory.UserCreationFactory;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.utils.CommandConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    private final ProcessBuilderAdapter processBuilderAdapter;

    @Autowired
    public UserService(ProcessBuilderAdapter processBuilderAdapter){
        this.processBuilderAdapter = processBuilderAdapter;
    }

    public boolean createUser(User user, SudoAuthentication sudoAuthentication) throws Exception {
        // Imprimir o nome do usuário que está executando o comando
        ProcessBuilder whoamiProcessBuilder = processBuilderAdapter.command("whoami").redirectOutput(ProcessBuilder.Redirect.PIPE);
        Process whoamiProcess = whoamiProcessBuilder.start();
        InputStream whoamiStdout = whoamiProcess.getInputStream();
        BufferedReader whoamiReader = new BufferedReader(new InputStreamReader(whoamiStdout));
        String whoamiLine;
        while ((whoamiLine = whoamiReader.readLine())!= null) {
            System.out.println("Running as user: " + whoamiLine);
        }

        // Executar o comando sudo
        ProcessBuilder processBuilder = processBuilderAdapter.command(
                CommandConstants.SUDO,
                "-u", "root",
                CommandConstants.SUDO_STDIN,
                CommandConstants.USER_ADD,
                user.getUser()
        ).redirectInput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
        outputStream.flush();
        process.getOutputStream().write(outputStream.toByteArray());
        process.getOutputStream().flush();
        process.getOutputStream().close();

        InputStream stderr = process.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stderr));
        String line;
        while ((line = reader.readLine())!= null) {
            System.err.println(line);
        }

        InputStream stdout = process.getInputStream();
        BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdout));
        String stdoutLine;
        while ((stdoutLine = stdoutReader.readLine())!= null) {
            System.out.println(stdoutLine);
        }

        int exitCode = process.waitFor();
        if (exitCode!= 0) {
            throw UserCreationFactory.createException(exitCode);
        }
        return true;
    }
}