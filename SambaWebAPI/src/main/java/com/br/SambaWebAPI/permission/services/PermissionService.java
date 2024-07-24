package com.br.SambaWebAPI.permission.services;

import com.br.SambaWebAPI.permission.enums.GroupPermissionEnum;
import com.br.SambaWebAPI.permission.enums.OwnerPermissionEnum;
import com.br.SambaWebAPI.permission.enums.PublicPermissionEnum;

import java.util.EnumMap;

public class PermissionService {

    public boolean chmodMapper(
            EnumMap<OwnerPermissionEnum, Integer> ownerPermission,
            EnumMap<GroupPermissionEnum, Integer> groupPermission,
            EnumMap<PublicPermissionEnum, Integer> publicPermission){

        if(ownerPermission.size() != 3 || groupPermission.size() != 3 || publicPermission.size() != 3){
            throw new IllegalArgumentException("Cada mapeamento deve ter exatamente 3 valores.");
        }
        for (Integer value : ownerPermission.values()) {
            if (value < 0 || value > 1) {
                throw new IllegalArgumentException("Os valores em mapping1 devem estar entre 0 e 1.");
            }
        }
        for (Integer value : groupPermission.values()) {
            if (value < 0 || value > 1) {
                throw new IllegalArgumentException("Os valores em mapping2 devem estar entre 0 e 1.");
            }
        }
        for (Integer value : publicPermission.values()) {
            if (value < 0 || value > 1) {
                throw new IllegalArgumentException("Os valores em mapping3 devem estar entre 0 e 1.");
            }
        }
        return true;
    }

    public void chmodCalculator(EnumMap<OwnerPermissionEnum,  Integer> ownerPermission,
                                   EnumMap<GroupPermissionEnum, Integer> groupPermission,
                                   EnumMap<PublicPermissionEnum, Integer> publicPermission){

        if(chmodMapper(ownerPermission,groupPermission,publicPermission)){
            Integer ownerPermissionValue = (int) ((ownerPermission.get(OwnerPermissionEnum.READ)* Math.pow(2,2)
                    + ownerPermission.get(OwnerPermissionEnum.WRITE) * Math.pow(2,1)
                    + ownerPermission.get(OwnerPermissionEnum.EXECUTE) * Math.pow(2,0)));

            Integer groupPermissionValue = (int) ((groupPermission.get(GroupPermissionEnum.READ)* Math.pow(2,2)
                    + groupPermission.get(GroupPermissionEnum.WRITE) * Math.pow(2,1)
                    + groupPermission.get(GroupPermissionEnum.EXECUTE) * Math.pow(2,0)));

            Integer publicPermissionValue = (int) ((publicPermission.get(PublicPermissionEnum.READ)* Math.pow(2,2)
                    + publicPermission.get(PublicPermissionEnum.WRITE) * Math.pow(2,1)
                    + publicPermission.get(PublicPermissionEnum.EXECUTE) * Math.pow(2,0)));

            String chmodValue = String.valueOf(ownerPermissionValue)
                    + String.valueOf(groupPermissionValue)
                    + String.valueOf(ownerPermissionValue) ;
            System.out.println(chmodValue);
        }
    }
}
