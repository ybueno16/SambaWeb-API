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
    }

}
