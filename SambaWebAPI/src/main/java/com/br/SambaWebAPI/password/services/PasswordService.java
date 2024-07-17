package com.br.SambaWebAPI.password.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.password.factory.PasswordCreationFactory;
import com.br.SambaWebAPI.utils.CommandConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Service
public class PasswordService {
    private final ProcessBuilderAdapter processBuilderAdapter;

    @Autowired
    public PasswordService(ProcessBuilderAdapter processBuilderAdapter){
        this.processBuilderAdapter = processBuilderAdapter;
    }
    public boolean createPassword(User user, SudoAuthentication sudoAuthentication) throws Exception {
        ProcessBuilder processBuilder = processBuilderAdapter.command(
                CommandConstants.BASH,
                CommandConstants.EXECUTE_COMMAND,
                CommandConstants.ECHO
                        + " \""
                        + sudoAuthentication.getSudoPassword()
                        + "\" | "
                        + CommandConstants.SUDO
                        + " "
                        + CommandConstants.SUDO_STDIN
                        + " "
                        + CommandConstants.BASH
                        + " "
                        + CommandConstants.EXECUTE_COMMAND
                        + " \""
                        + CommandConstants.ECHO
                        + " '"
                        + user.getUser()
                        + ":"
                        + user.getPassword()
                        + "'"
                        + " | "
                        + CommandConstants.PASSWD_ADD
                        + "\""
        ).redirectInput(ProcessBuilder.Redirect.PIPE);


        Process process = processBuilder.start();

        int exitCode = process.waitFor();
        if (exitCode!= 0) {
            throw PasswordCreationFactory.createException(exitCode);
        }

        return true;
    }
}
