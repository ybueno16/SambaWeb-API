package com.br.SambaWebAPI.permission.models.dto;

import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.permission.models.GroupPermission;
import com.br.SambaWebAPI.permission.models.OwnerPermission;
import com.br.SambaWebAPI.permission.models.PublicPermission;

public class PermissionDTO {
    private OwnerPermission ownerPermission;
    private GroupPermission groupPermission;
    private PublicPermission publicPermission;
    private Folder folder;
    private SudoAuthentication sudoAuthentication;

    public OwnerPermission getOwnerPermission() {
        return ownerPermission;
    }

    public void setOwnerPermission(OwnerPermission ownerPermission) {
        this.ownerPermission = ownerPermission;
    }

    public GroupPermission getGroupPermission() {
        return groupPermission;
    }

    public void setGroupPermission(GroupPermission groupPermission) {
        this.groupPermission = groupPermission;
    }

    public PublicPermission getPublicPermission() {
        return publicPermission;
    }

    public void setPublicPermission(PublicPermission publicPermission) {
        this.publicPermission = publicPermission;
    }

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
