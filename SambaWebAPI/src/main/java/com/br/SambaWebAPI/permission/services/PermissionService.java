package com.br.SambaWebAPI.permission.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.folder.services.FolderService;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.permission.exceptions.PermissionAddException;
import com.br.SambaWebAPI.permission.factory.PermissionAddFactory;
import com.br.SambaWebAPI.permission.models.GroupPermission;
import com.br.SambaWebAPI.permission.models.OwnerPermission;
import com.br.SambaWebAPI.permission.models.PublicPermission;
import com.br.SambaWebAPI.utils.PermissionCodeCalculator;
import com.br.SambaWebAPI.utils.CommandConstants;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

  private ProcessBuilderAdapter processBuilderAdapter;

  private PermissionCodeCalculator permissionCodeCalculator;

  @Autowired FolderService folderService;

  private String homeDir;

  @Autowired
  public PermissionService(
          ProcessBuilderAdapter processBuilderAdapter,
          FolderService folderService ,
          PermissionCodeCalculator permissionCodeCalculator) throws IOException, InterruptedException {
    this.processBuilderAdapter = processBuilderAdapter;
    this.folderService = folderService;
    this.permissionCodeCalculator = permissionCodeCalculator;
    this.homeDir = folderService.getHomeDir();
  }

  public String chmodCalculator(
          OwnerPermission ownerPermission,
          GroupPermission groupPermission,
          PublicPermission publicPermission) {

    int  ownerPermissionValue = permissionCodeCalculator.chmodCalculator(
            ownerPermission
    );

    int groupPermissionValue =
            permissionCodeCalculator.chmodCalculator(
                    groupPermission);
    int publicPermissionValue =
            permissionCodeCalculator.chmodCalculator(
                    publicPermission);

    return String.format(
            "%03o", (ownerPermissionValue * 64) + (groupPermissionValue * 8) + publicPermissionValue);
  }

  public boolean managePermission(
          OwnerPermission ownerPermission,
          GroupPermission groupPermission,
          PublicPermission publicPermission,
          SudoAuthentication sudoAuthentication,
          Folder folder)
          throws Exception, PermissionAddException {

    processBuilderAdapter.command("exit");
    String getPermissionCode = chmodCalculator(ownerPermission, groupPermission, publicPermission);

    ProcessBuilder processBuilder =
            processBuilderAdapter.command(
                    CommandConstants.SUDO,
                    CommandConstants.SUDO_STDIN,
                    CommandConstants.CHMOD,
                    getPermissionCode,
                    homeDir + "/" + folder.getPath());

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
    return true;
  }
}
