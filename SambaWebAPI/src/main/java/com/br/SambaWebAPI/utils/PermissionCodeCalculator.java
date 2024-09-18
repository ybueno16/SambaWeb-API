package com.br.SambaWebAPI.utils;

import com.br.SambaWebAPI.permission.models.PermissionInterface;
import org.springframework.stereotype.Component;

@Component
public class PermissionCodeCalculator {
    public int chmodCalculator(PermissionInterface permissionInterface) {
        return (int) ((permissionInterface.getWrite() * Math.pow(2, 2))
                + (permissionInterface.getRead() * Math.pow(2, 1))
                + permissionInterface.getExecute());
    }
}
