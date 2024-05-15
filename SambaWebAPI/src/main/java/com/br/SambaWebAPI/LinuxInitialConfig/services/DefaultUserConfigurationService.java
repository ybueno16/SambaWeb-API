package com.br.SambaWebAPI.LinuxInitialConfig.services;

import com.br.SambaWebAPI.LinuxInitialConfig.models.Group;
import com.br.SambaWebAPI.LinuxInitialConfig.models.SudoAuthentication;
import com.br.SambaWebAPI.LinuxInitialConfig.models.User;
import com.br.SambaWebAPI.exceptions.AddUserToGroupException;
import com.br.SambaWebAPI.exceptions.GroupCreationException;
import com.br.SambaWebAPI.exceptions.UserCreationException;
import com.br.SambaWebAPI.exceptions.PasswordCreationException;
import com.br.SambaWebAPI.utils.factorys.AddUserToGroupFactory;
import com.br.SambaWebAPI.utils.factorys.GroupCreationFactory;
import com.br.SambaWebAPI.utils.factorys.PasswordCreationFactory;
import com.br.SambaWebAPI.utils.factorys.UserCreationFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;


@Service
public class DefaultUserConfigurationService {

    private final ProcessBuilder processBuilder;


    public DefaultUserConfigurationService() {
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

    public boolean createGroup(Group group, SudoAuthentication sudoAuthentication) throws InterruptedException, IOException, GroupCreationException, PasswordCreationException {

        processBuilder.command("sudo", "-S", "groupadd", group.getName());
        processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        OutputStream outputStream = process.getOutputStream();
        outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
        outputStream.flush();
        outputStream.close();
        process.waitFor();

        int exitCode = process.waitFor();
        if (exitCode != 0) {

            throw GroupCreationFactory.createException(exitCode);
        }

        return true;
    }

    public boolean addUserToGroup(Group group, User user, SudoAuthentication sudoAuthentication) throws IOException, InterruptedException, GroupCreationException, AddUserToGroupException {
        processBuilder.command("sudo", "-S", "/usr/sbin/usermod", "-aG", group.getName(), user.getUser());
        processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        OutputStream outputStream = process.getOutputStream();
        outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
        outputStream.flush();
        outputStream.close();


        int exitCode = process.waitFor();
        if (exitCode!= 0) {
            throw AddUserToGroupFactory.createException(exitCode);
        }

        return true;
    }
    
}