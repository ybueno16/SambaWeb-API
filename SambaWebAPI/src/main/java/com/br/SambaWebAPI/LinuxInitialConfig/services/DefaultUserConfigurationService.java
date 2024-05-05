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
                case 1:
                    throw new UserCreationException(UserCreationErrorCode.CANT_UPDT_PASSWD_FILE);
                case 4:
                    throw new UserCreationException(UserCreationErrorCode.USR_ALREADY_EXISTS);
                case 6:
                    throw new UserCreationException(UserCreationErrorCode.SPECIFIED_GROUP_DONT_EXIST);
                case 9:
                    throw new UserCreationException(UserCreationErrorCode.USR_ALREADY_IN_USE);
                case 10:
                    throw new UserCreationException(UserCreationErrorCode.CANT_UPDT_GROUP_FILE);
                case 12:
                    throw new UserCreationException(UserCreationErrorCode.CANT_CREATE_HOME_DIR);
                case 13:
                    throw new UserCreationException(UserCreationErrorCode.CANT_CREATE_MAIL_SPOOL);
                case 14:
                    throw new UserCreationException(UserCreationErrorCode.CANT_UPDATE_SELINUX);
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
                case 2:
                    throw new PasswordCreationException(PasswordCreationErrorCode.UNKNOWN_USER);
                case 252:
                    throw new PasswordCreationException(PasswordCreationErrorCode.UNKNOWN_USERNAME);
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
                case 4:
                    throw new GroupCreationException(GroupCreationErrorCode.GID_ALREADY_EXISTS);
                case 9:
                    throw new GroupCreationException(GroupCreationErrorCode.GROUP_NAME_NOT_UNIQUE);
                case 10:
                    throw new GroupCreationException(GroupCreationErrorCode.CANT_UPDT_GROUP_FILE);
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