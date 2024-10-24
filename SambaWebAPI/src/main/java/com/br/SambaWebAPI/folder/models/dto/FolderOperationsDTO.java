package com.br.SambaWebAPI.folder.models.dto;

import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.password.models.SudoAuthentication;

public class FolderOperationsDTO {
    private Folder folder;
    private SudoAuthentication sudoAuthentication;

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public SudoAuthentication getSudoAuthentication() {
        return sudoAuthentication;
    }

    public void setSudoAuthentication(SudoAuthentication sudoAuthentication) {
        this.sudoAuthentication = sudoAuthentication;
    }
}
