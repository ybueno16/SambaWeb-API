package com.br.SambaWebAPI.folder.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.adapter.impl.ProcessBuilderAdapterImpl;
import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.factory.UserCreationFactory;
import com.br.SambaWebAPI.utils.CommandConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

@Service
public class FolderService {

    private ProcessBuilderAdapter processBuilderAdapter;

    @Autowired
    public FolderService(ProcessBuilderAdapter processBuilderAdapter){
        this.processBuilderAdapter = processBuilderAdapter;
    }

    public boolean createFolder(Folder folder, SudoAuthentication sudoAuthentication) throws Exception{
        processBuilderAdapter = new ProcessBuilderAdapterImpl();

        processBuilderAdapter.command("exit");
        String homeDir = getHomeDir();

        ProcessBuilder processBuilder = processBuilderAdapter.command(
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.MKDIR,
                homeDir + "/" + folder.getPath()
        ).redirectInput(ProcessBuilder.Redirect.PIPE);

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

    private String getHomeDir() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = processBuilderAdapter.command(
                CommandConstants.BASH,
                CommandConstants.EXECUTE_COMMAND,
                CommandConstants.ECHO,
                "$HOME"
        );

        Process process = processBuilder.start();

        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        String homeDir = "";
        while ((line = reader.readLine())!= null) {
            homeDir = line.trim();
        }

        process.waitFor();
        return homeDir;
    }
}
