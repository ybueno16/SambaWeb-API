package com.br.SambaWebAPI.folder.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.adapter.impl.ProcessBuilderAdapterImpl;
import com.br.SambaWebAPI.folder.exceptions.FolderCreationException;
import com.br.SambaWebAPI.folder.factory.FolderCreationFactory;
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

  public void createFolder(Folder folder, SudoAuthentication sudoAuthentication) throws Exception {
    processBuilderAdapter = new ProcessBuilderAdapterImpl();

    processBuilderAdapter.command("exit");
    String homeDir = getHomeDir();

    ProcessBuilder processBuilder =
        processBuilderAdapter
            .command(
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.MKDIR,
                homeDir + "/" + folder.getPath())
            .redirectInput(ProcessBuilder.Redirect.PIPE);

    Process process = processBuilder.start();

    OutputStream outputStream = process.getOutputStream();
    outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
    outputStream.flush();
    outputStream.close();
    process.waitFor();

    int exitCode = process.waitFor();

    if (exitCode != 0) {
      throw FolderCreationFactory.createException();
    }
  }

  public String getHomeDir() throws IOException, InterruptedException, FolderCreationException {
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
      throw FolderCreationFactory.createException();
    }

    return homeDir;
  }
}
