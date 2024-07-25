package com.br.SambaWebAPI.permission.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.adapter.impl.ProcessBuilderAdapterImpl;
import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.folder.services.FolderService;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.permission.exceptions.PermissionAddException;
import com.br.SambaWebAPI.permission.factory.PermissionAddFactory;
import com.br.SambaWebAPI.permission.models.GroupPermission;
import com.br.SambaWebAPI.permission.models.OwnerPermission;
import com.br.SambaWebAPI.permission.models.PublicPermission;
import com.br.SambaWebAPI.utils.CommandConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class PermissionService {

    private ProcessBuilderAdapter processBuilderAdapter;
    private FolderService folderService;

    @Autowired
    public PermissionService(ProcessBuilderAdapter processBuilderAdapter, FolderService folderService)
            throws IOException, InterruptedException {
        this.processBuilderAdapter = processBuilderAdapter;
        this.folderService = folderService;
        this.homeDir = folderService.getHomeDir(); // Initialize homeDir here
    }

    private String homeDir;

    public String chmodCalculator(OwnerPermission ownerPermission,
                                  GroupPermission groupPermission,
                                  PublicPermission publicPermission) {

        int ownerPermissionValue = (int) ((ownerPermission.getWrite() * Math.pow(2, 2))
                + (ownerPermission.getRead() * Math.pow(2, 1))
                + ownerPermission.getExecute());

        int groupPermissionValue = (int) ((groupPermission.getWrite() * Math.pow(2, 2))
                + (groupPermission.getRead() * Math.pow(2, 1))
                + groupPermission.getExecute());

        int publicPermissionValue = (int) ((publicPermission.getWrite() * Math.pow(2, 2))
                + (publicPermission.getRead() * Math.pow(2, 1))
                + publicPermission.getExecute());

        return String.format("%03o",
                (ownerPermissionValue * 64)
                        + (groupPermissionValue * 8)
                        + publicPermissionValue);
    }

    public void managePermission(OwnerPermission ownerPermission,
                                 GroupPermission groupPermission,
                                 PublicPermission publicPermission,
                                 SudoAuthentication sudoAuthentication,
                                 Folder folder) throws Exception, PermissionAddException {
        processBuilderAdapter = new ProcessBuilderAdapterImpl();

        processBuilderAdapter.command("exit");
        String getPermissionCode = chmodCalculator(ownerPermission, groupPermission, publicPermission);

        ProcessBuilder processBuilder = processBuilderAdapter.command(
                CommandConstants.SUDO,
                CommandConstants.SUDO_STDIN,
                CommandConstants.CHMOD,
                getPermissionCode,
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
            throw PermissionAddFactory.createException();
        }
    }

}