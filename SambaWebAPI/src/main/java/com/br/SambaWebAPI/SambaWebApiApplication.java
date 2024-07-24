package com.br.SambaWebAPI;

import com.br.SambaWebAPI.permission.enums.GroupPermissionEnum;
import com.br.SambaWebAPI.permission.enums.OwnerPermissionEnum;
import com.br.SambaWebAPI.permission.enums.PublicPermissionEnum;
import com.br.SambaWebAPI.permission.services.PermissionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.EnumMap;

@SpringBootApplication
public class SambaWebApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SambaWebApiApplication.class, args);
        EnumMap<OwnerPermissionEnum, Integer> ownerPermission = new EnumMap<>(OwnerPermissionEnum.class);
        EnumMap<GroupPermissionEnum, Integer> groupPermission = new EnumMap<>(GroupPermissionEnum.class);
        EnumMap<PublicPermissionEnum, Integer> publicPermission = new EnumMap<>(PublicPermissionEnum.class);

        // Defina os valores de permissão
        ownerPermission.put(OwnerPermissionEnum.READ, 1);
        ownerPermission.put(OwnerPermissionEnum.WRITE, 1);
        ownerPermission.put(OwnerPermissionEnum.EXECUTE, 1);

        groupPermission.put(GroupPermissionEnum.READ, 1);
        groupPermission.put(GroupPermissionEnum.WRITE, 1);
        groupPermission.put(GroupPermissionEnum.EXECUTE, 1);

        publicPermission.put(PublicPermissionEnum.READ, 1);
        publicPermission.put(PublicPermissionEnum.WRITE, 1);
        publicPermission.put(PublicPermissionEnum.EXECUTE, 1);

        // Crie uma instância de PermissionService
        PermissionService permissionService = new PermissionService();

        // Chame o método chmodCalculator passando os EnumMaps
        permissionService.chmodCalculator(ownerPermission, groupPermission, publicPermission);
    }

}
