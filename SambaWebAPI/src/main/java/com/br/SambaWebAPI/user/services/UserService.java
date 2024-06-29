package com.br.SambaWebAPI.user.services;

import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.adapter.UserAdapter;
import com.br.SambaWebAPI.user.factory.UserCreationFactory;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.utils.CommandConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
public class UserService {

    private final UserAdapter userAdapter;
    private final ProcessBuilder processBuilder;
    @Autowired
    public UserService(UserAdapter userAdapter ){
       this.userAdapter = userAdapter;
        processBuilder = new ProcessBuilder();
    }

    public void createUser(User user, SudoAuthentication sudoAuthentication) throws Exception {

        userAdapter.command(
                CommandConstants.SUDO,
                CommandConstants.ADD_USER,
                CommandConstants.CREATE_HOME_DIR,
                CommandConstants.SUDO_PASSWORD_OPTION,
                user.getUser());
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
    }


}
