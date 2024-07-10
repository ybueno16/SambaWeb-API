    package com.br.SambaWebAPI.user.services;

    import com.br.SambaWebAPI.password.models.SudoAuthentication;
    import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
    import com.br.SambaWebAPI.user.factory.UserCreationFactory;
    import com.br.SambaWebAPI.user.models.User;
    import com.br.SambaWebAPI.utils.CommandConstants;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.io.InputStream;
    import java.io.OutputStream;
    import java.util.concurrent.TimeUnit;

    @Service
    public class UserService {
        private final ProcessBuilderAdapter processBuilderAdapter;

        @Autowired
        public UserService(ProcessBuilderAdapter processBuilderAdapter){
            this.processBuilderAdapter = processBuilderAdapter;
        }

        public boolean createUser(User user, SudoAuthentication sudoAuthentication) throws Exception {

            ProcessBuilder processBuilder = processBuilderAdapter.command(
                    CommandConstants.SUDO,
                    CommandConstants.SUDO_STDIN,
                    CommandConstants.USER_ADD,
                    user.getUser()
            ).redirectInput(ProcessBuilder.Redirect.PIPE);

            Process process = processBuilder.start();

            OutputStream outputStream = process.getOutputStream();
            outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
            outputStream.flush();
            outputStream.close();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw UserCreationFactory.createException(exitCode);
            }
            return true;
        }


    }
