package com.br.SambaWebAPI.login.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.login.factory.LoginFactory;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.utils.CommandConstants;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginService {
  private ProcessBuilderAdapter processBuilderAdapter;

  @Autowired
  public LoginService(ProcessBuilderAdapter processBuilderAdapter) {
    this.processBuilderAdapter = processBuilderAdapter;
  }

  public boolean login(User user) throws Exception {
    processBuilderAdapter.command(CommandConstants.EXIT_TERMINAL);

    ProcessBuilder processBuilder =
        processBuilderAdapter.command(
            CommandConstants.VERIFY_IDENTITY,
            user.getUser(),
            CommandConstants.EXECUTE_COMMAND,
            "true");
    Process process = processBuilder.start();

    int exitCode = process.waitFor();
    if (exitCode != 0) {
      LoginFactory.createException(exitCode);
    }
    return true;
  }
}
