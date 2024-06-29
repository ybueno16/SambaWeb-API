package com.br.SambaWebAPI.group.services;

import com.br.SambaWebAPI.group.models.Group;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.group.exceptions.AddUserToGroupException;
import com.br.SambaWebAPI.group.exceptions.GroupCreationException;
import com.br.SambaWebAPI.password.exceptions.PasswordCreationException;
import com.br.SambaWebAPI.group.factory.AddUserToGroupFactory;
import com.br.SambaWebAPI.group.factory.GroupCreationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class GroupService {
    private final ProcessBuilder processBuilder;
    @Autowired
    public GroupService(ProcessBuilder processBuilder) {
        this.processBuilder = processBuilder;
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
