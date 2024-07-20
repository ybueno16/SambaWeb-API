package com.br.SambaWebAPI.folder.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.adapter.impl.ProcessBuilderAdapterImpl;
import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.user.factory.UserCreationFactory;
import com.br.SambaWebAPI.utils.CommandConstants;
import org.springframework.beans.factory.annotation.Autowired;

public class FolderService {

    private ProcessBuilderAdapter processBuilderAdapter;

    @Autowired
    public FolderService(ProcessBuilderAdapter processBuilderAdapter){
        this.processBuilderAdapter = processBuilderAdapter;
    }

    public boolean createFolder(Folder folder) throws Exception{
        processBuilderAdapter = new ProcessBuilderAdapterImpl();

        processBuilderAdapter.command("exit");

        ProcessBuilder processBuilder = processBuilderAdapter.command(
                CommandConstants.BASH,
                CommandConstants.EXECUTE_COMMAND,
                CommandConstants.MKDIR,
                folder.getPath()
        ).redirectInput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw UserCreationFactory.createException(exitCode);
        }

        return true;
    }
}
