package com.br.SambaWebAPI.config;

import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.adapter.UserAdapter;
import com.br.SambaWebAPI.user.adapter.impl.UserAdapterImpl;
import com.br.SambaWebAPI.user.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.OutputStream;

@Configuration
public class AppConfig {

    @Bean
    public ProcessBuilder processBuilder() {
        return new ProcessBuilder();
    }

    @Bean
    public UserAdapter userAdapter() {
        return new UserAdapterImpl(); // or any other implementation
    }
}
