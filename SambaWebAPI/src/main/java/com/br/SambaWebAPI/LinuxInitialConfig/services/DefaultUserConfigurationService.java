package com.br.SambaWebAPI.LinuxInitialConfig.services;

import com.br.SambaWebAPI.LinuxInitialConfig.models.Group;
import com.br.SambaWebAPI.LinuxInitialConfig.models.SudoAuthentication;
import com.br.SambaWebAPI.LinuxInitialConfig.models.User;
import com.br.SambaWebAPI.exceptions.PasswordCreationException;
import com.br.SambaWebAPI.exceptions.UserCreationExceptions;
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

    public boolean cadastrarUsuario(User user, SudoAuthentication sudoAuthentication) throws UserCreationExceptions, InterruptedException, IOException {

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
                    throw new UserCreationExceptions(UserCreationErrorCode.GROUP_DOES_NOT_EXIST);
                case 3:
                    throw new UserCreationExceptions(UserCreationErrorCode.DIR_CANT_BE_CREATED);
                case 4:
                    throw new UserCreationExceptions(UserCreationErrorCode.USR_CANT_BE_CREATED);
                case 10:
                    throw new UserCreationExceptions(UserCreationErrorCode.CANT_UPDT_PASSWD);
                default:
                    throw new UserCreationExceptions(UserCreationErrorCode.GENERIC_ERROR);
            }
        }

        return true;
    }

    public boolean cadastrarSenha(User user) throws Exception {
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

    public boolean criarGrupo(Group group, SudoAuthentication sudoAuthentication) throws IOException {
        processBuilder.command("sudo","-S", "groupadd", group.getName());
        processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        OutputStream outputStream = process.getOutputStream();
        outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
        return true;
    }
}