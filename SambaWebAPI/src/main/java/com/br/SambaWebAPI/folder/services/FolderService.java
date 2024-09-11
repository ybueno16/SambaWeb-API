package com.br.SambaWebAPI.folder.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.folder.factory.CreateFolderFactory;
import com.br.SambaWebAPI.folder.factory.DeleteFolderFactory;
import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.utils.CommandConstants;
import java.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FolderService {

  private ProcessBuilderAdapter processBuilderAdapter;

  @Autowired
  public FolderService(ProcessBuilderAdapter processBuilderAdapter) {
    this.processBuilderAdapter = processBuilderAdapter;
  }

  public boolean createFolder(Folder folder, SudoAuthentication sudoAuthentication)
          throws Exception {

    processBuilderAdapter.command("exit");

    String homeDir = getHomeDir();

    ProcessBuilder processBuilder =
            processBuilderAdapter.command(
                    CommandConstants.SUDO,
                    CommandConstants.SUDO_STDIN,
                    CommandConstants.MKDIR,
                    homeDir + "/" + folder.getPath());

    Process process = processBuilder.start();

    OutputStream outputStream = process.getOutputStream();
    outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
    outputStream.flush();
    outputStream.close();
    process.waitFor();

    int exitCode = process.waitFor();

    if (exitCode != 0) {
      throw CreateFolderFactory.createException();
    }
    return true;
  }

  public String getHomeDir() throws IOException, InterruptedException {
    ProcessBuilder processBuilder =
            processBuilderAdapter.command(
                    CommandConstants.SUDO,
                    CommandConstants.BASH,
                    CommandConstants.EXECUTE_COMMAND,
                    CommandConstants.ECHO + " " + "$HOME");

    Process process = processBuilder.start();

    InputStream inputStream = process.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

    String line;
    String homeDir = "";
    while ((line = reader.readLine()) != null) {
      homeDir = line.trim();
    }

    int exitCode = process.waitFor();
    if (exitCode != 0) {
      throw new IOException(
              "Generic error. An unknown error occurred during validation of the environment variable");
    }

    return homeDir;
  }

  public boolean removeFolder(Folder folder, SudoAuthentication sudoAuthentication) throws Exception {
    processBuilderAdapter.command("exit");
    String homeDir = getHomeDir();

    ProcessBuilder processBuilder =
            processBuilderAdapter
                    .command(
                            CommandConstants.SUDO,
                            CommandConstants.SUDO_STDIN,
                            CommandConstants.REMOVE,
                            CommandConstants.REMOVE_RECURSIVE,
                            homeDir + "/" + folder.getPath());

    Process process = processBuilder.start();

    OutputStream outputStream = process.getOutputStream();
    outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
    outputStream.flush();
    outputStream.close();

    process.waitFor();

    int exitCode = process.exitValue();

    if (exitCode != 0) {
      throw DeleteFolderFactory.createException();
    }
    return true;
  }
}
