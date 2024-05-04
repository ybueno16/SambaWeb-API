package com.br.SambaWebAPI.LinuxInitialConfig.services;

import com.br.SambaWebAPI.LinuxInitialConfig.models.Group;
import com.br.SambaWebAPI.LinuxInitialConfig.models.SudoAuthentication;
import com.br.SambaWebAPI.LinuxInitialConfig.models.User;
import com.br.SambaWebAPI.exceptions.GroupCreationException;
import com.br.SambaWebAPI.exceptions.UserCreationException;
import com.br.SambaWebAPI.exceptions.PasswordCreationException;
import com.br.SambaWebAPI.utils.enums.UserManagent.GroupCreationErrorCode;
import com.br.SambaWebAPI.utils.enums.UserManagent.PasswordCreationErrorCode;
import com.br.SambaWebAPI.utils.enums.UserManagent.UserCreationErrorCode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;


@Service
public class DefaultUserConfigurationService {

    private final ProcessBuilder processBuilder;


    public DefaultUserConfigurationService() {
        processBuilder = new ProcessBuilder();
    }

    public boolean registerUser(User user, SudoAuthentication sudoAuthentication) throws InterruptedException, IOException, UserCreationException {

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
            switch (exitCode) {
                case 2:
                    throw new UserCreationException(UserCreationErrorCode.GROUP_DOES_NOT_EXIST);
                case 3:
                    throw new UserCreationException(UserCreationErrorCode.DIR_CANT_BE_CREATED);
                case 4:
                    throw new UserCreationException(UserCreationErrorCode.USR_CANT_BE_CREATED);
                case 10:
                    throw new UserCreationException(UserCreationErrorCode.CANT_UPDT_PASSWD);
                default:
                    throw new UserCreationException(UserCreationErrorCode.GENERIC_ERROR);
            }
        }

        return true;
    }

    public boolean registerPassword(User user) throws Exception {
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
            switch (exitCode) {
                case 3:
                    throw new PasswordCreationException(PasswordCreationErrorCode.CANT_READ_PASSWD_FILE);
                case 4:
                    throw new PasswordCreationException(PasswordCreationErrorCode.CANT_WRITE_PASSWD_FILE);
                case 5:
                    throw new PasswordCreationException(PasswordCreationErrorCode.INVALID_PASSWD);
                case 6:
                    throw new PasswordCreationException(PasswordCreationErrorCode.ERROR_CONFIRMATION_PASSWD);
                default:
                    throw new PasswordCreationException(PasswordCreationErrorCode.GENERIC_ERROR);
            }
        }

        return true;
    }

    public boolean registerGroup(Group group, SudoAuthentication sudoAuthentication) throws InterruptedException, IOException, GroupCreationException {

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
            switch (exitCode) {
                case 2:
                    throw new GroupCreationException(GroupCreationErrorCode.GROUP_ALREADY_EXISTS);
                case 3:
                    throw new GroupCreationException(GroupCreationErrorCode.INVALID_NAME_GROUP);
                case 5:
                    throw new GroupCreationException(GroupCreationErrorCode.SYSTEM_ERROR);
                case 6:
                    throw new GroupCreationException(GroupCreationErrorCode.PERMISSION_DENIED);
                default:
                    throw new GroupCreationException(GroupCreationErrorCode.GENERIC_ERROR);
            }
        }

        return true;
    }

    public boolean addUserToGroup(Group group, User user, SudoAuthentication sudoAuthentication) throws IOException {
        processBuilder.command("sudo", "-S", "usermod -aG",group.getName()," ", user.getUser());
        processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        OutputStream outputStream = process.getOutputStream();
        outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());


        return true;
    }
    
}