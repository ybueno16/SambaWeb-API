package com.br.SambaWebAPI.login.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.login.factory.LoginFactory;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.utils.CommandConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
public class LoginService {
  private ProcessBuilderAdapter processBuilderAdapter;

  @Autowired
  public LoginService(ProcessBuilderAdapter processBuilderAdapter) {
    this.processBuilderAdapter = processBuilderAdapter;
  }

  public void login(User user, SudoAuthentication sudoAuthentication) throws Exception {
    processBuilderAdapter.command(CommandConstants.EXIT_TERMINAL);

    ProcessBuilder processBuilder =
            processBuilderAdapter.command(
                    CommandConstants.SUDO,
                    CommandConstants.USER_ARGUMENT,
                    sudoAuthentication.getSudoUser(),
                    CommandConstants.VERIFY_IDENTITY,
                    user.getUser(),
                    CommandConstants.EXECUTE_COMMAND,
                    "true");
    Process process = processBuilder.start();

    OutputStream outputStream = process.getOutputStream();

    outputStream.write((user.getPassword() + "\n").getBytes());
    outputStream.flush();
    outputStream.close();
    process.waitFor();

    int exitCode = process.waitFor();
    if (exitCode != 0) {
      LoginFactory.createException(exitCode);
    }
  }
}
