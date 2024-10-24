package com.br.SambaWebAPI.group.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.group.exceptions.AddUserToGroupException;
import com.br.SambaWebAPI.group.exceptions.CreateGroupException;
import com.br.SambaWebAPI.group.exceptions.DeleteGroupException;
import com.br.SambaWebAPI.group.factory.AddUserToGroupFactory;
import com.br.SambaWebAPI.group.factory.CreateGroupFactory;
import com.br.SambaWebAPI.group.factory.DeleteGroupFactory;
import com.br.SambaWebAPI.group.models.Group;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.utils.CommandConstants;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
  private final ProcessBuilderAdapter processBuilderAdapter;

  @Autowired
  public GroupService(ProcessBuilderAdapter processBuilderAdapter) {
    this.processBuilderAdapter = processBuilderAdapter;
  }

  public boolean createGroup(Group group, SudoAuthentication sudoAuthentication)
      throws InterruptedException, IOException, CreateGroupException {
    processBuilderAdapter.command(CommandConstants.EXIT_TERMINAL);

    ProcessBuilder processBuilder =
        processBuilderAdapter.command(
            CommandConstants.SUDO,
            CommandConstants.SUDO_STDIN,
            CommandConstants.GROUP_ADD,
            group.getName());

    Process process = processBuilder.start();

    OutputStream outputStream = process.getOutputStream();
    outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
    outputStream.flush();
    outputStream.close();
    process.waitFor();

    int exitCode = process.waitFor();
    if (exitCode != 0) {

      throw CreateGroupFactory.createException(exitCode);
    }
    return true;
  }

  public boolean addUserToGroup(Group group, User user, SudoAuthentication sudoAuthentication)
      throws IOException, InterruptedException, AddUserToGroupException {
    processBuilderAdapter.command(CommandConstants.EXIT_TERMINAL);

    ProcessBuilder processBuilder =
        processBuilderAdapter.command(
            CommandConstants.SUDO,
            CommandConstants.SUDO_STDIN,
            CommandConstants.USER_MOD,
            CommandConstants.ADD_GROUP_OPTION,
            group.getName(),
            user.getUser());

    Process process = processBuilder.start();

    OutputStream outputStream = process.getOutputStream();
    outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
    outputStream.flush();
    outputStream.close();

    int exitCode = process.waitFor();
    if (exitCode != 0) {
      throw AddUserToGroupFactory.createException(exitCode);
    }
    return true;
  }

  public boolean deleteGroup(Group group, SudoAuthentication sudoAuthentication)
      throws IOException, InterruptedException, DeleteGroupException {
    processBuilderAdapter.command(CommandConstants.EXIT_TERMINAL);

    ProcessBuilder processBuilder =
        processBuilderAdapter.command(
            CommandConstants.SUDO,
            CommandConstants.SUDO_STDIN,
            CommandConstants.GROUP_DEL,
            group.getName());

    Process process = processBuilder.start();

    OutputStream outputStream = process.getOutputStream();
    outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
    outputStream.flush();
    outputStream.close();

    int exitCode = process.waitFor();
    if (exitCode != 0) {
      throw DeleteGroupFactory.createException(exitCode);
    }

    return true;
  }
}
