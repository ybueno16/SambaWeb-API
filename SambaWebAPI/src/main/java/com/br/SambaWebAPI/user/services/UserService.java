package com.br.SambaWebAPI.user.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.adapter.impl.ProcessBuilderAdapterImpl;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.factory.UserCreationFactory;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.utils.CommandConstants;
import java.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private ProcessBuilderAdapter processBuilderAdapter;

  @Autowired
  public UserService(ProcessBuilderAdapter processBuilderAdapter) {
    this.processBuilderAdapter = processBuilderAdapter;
  }

  public boolean createUser(User user, SudoAuthentication sudoAuthentication) throws Exception {
    // TODO: decorador que instancia novo processbuilder nos metódos e já chama
    // o exit
    processBuilderAdapter = new ProcessBuilderAdapterImpl();

    processBuilderAdapter.command("exit");

    ProcessBuilder processBuilder =
        processBuilderAdapter
            .command(
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
                    + CommandConstants.USER_ADD
                    + " "
                    + user.getUser())
            .redirectInput(ProcessBuilder.Redirect.PIPE);

    Process process = processBuilder.start();

    int exitCode = process.waitFor();

    if (exitCode != 0) {
      throw UserCreationFactory.createException(exitCode);
    }

    return true;
  }

  public boolean removeUser(User user, SudoAuthentication sudoAuthentication) throws Exception {
    processBuilderAdapter.command("exit");

    ProcessBuilder processBuilder =
        processBuilderAdapter
            .command(
                CommandConstants.ECHO,
                sudoAuthentication.getSudoPassword(),
                "\" | ",
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.USER_DEL,
                user.getUser())
            .redirectInput(ProcessBuilder.Redirect.PIPE);

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

  public boolean getUser(User user) throws Exception {
    ProcessBuilder processBuilder =
        processBuilderAdapter.command(
            CommandConstants.BASH,
            CommandConstants.EXECUTE_COMMAND,
            CommandConstants.GET_USER + user.getUser());
    Process process = processBuilder.start();

    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

    reader.read();
    reader.close();

    int exitCode = process.waitFor();
    return exitCode == 0;
  }

  public boolean createSambaUser(User user, SudoAuthentication sudoAuthentication)
      throws Exception {
    processBuilderAdapter = new ProcessBuilderAdapterImpl();

    createUser(user, sudoAuthentication);

    ProcessBuilder processBuilder =
        processBuilderAdapter.command(
            CommandConstants.BASH,
            CommandConstants.EXECUTE_COMMAND,
            CommandConstants.SUDO
                + " "
                + CommandConstants.SUDO_STDIN
                + " "
                + CommandConstants.USER_ADD_SMB
                + " "
                + user.getUser());

    Process process = processBuilder.start();
    OutputStream outputStream = process.getOutputStream();
    outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
    outputStream.flush();

    outputStream.write((user.getPassword() + "\n").getBytes());
    outputStream.flush();

    outputStream.write((user.getPassword() + "\n").getBytes());
    outputStream.flush();
    outputStream.close();

    process.waitFor();

    int exitCode = process.waitFor();
    if (exitCode != 0) {
      throw UserCreationFactory.createException(exitCode);
    }

    return true;
  }

  public boolean removeSambaUser(User user, SudoAuthentication sudoAuthentication)
      throws Exception {
    processBuilderAdapter = new ProcessBuilderAdapterImpl();

    ProcessBuilder processBuilder =
        processBuilderAdapter.command(
            CommandConstants.BASH,
            CommandConstants.EXECUTE_COMMAND,
            CommandConstants.SUDO
                + " "
                + CommandConstants.SUDO_STDIN
                + " "
                + CommandConstants.USER_DEL_SMB
                + " "
                + user.getUser());

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
}
