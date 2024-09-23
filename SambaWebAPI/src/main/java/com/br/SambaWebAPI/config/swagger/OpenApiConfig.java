package com.br.SambaWebAPI.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de documentação de API")
                        .version("0.0.1")
                        .description("Documentação da API feito por @ybueno16")
                        .summary("(C) 2024 Yuri Bueno")
                        .termsOfService("https://github.com/ybueno16")
                        .contact(new Contact()
                                .name("Yuri Bueno")
                                .url("https://github.com/ybueno16")
                                .email("smbdweb@gmail.com"))
                        .license(new License()
                                .name("Licença")
                                .url("https://github.com/ybueno16/SambaWeb-API/blob/master/LICENSE")))
                .addServersItem(new Server().url("http://localhost:8080")
                        .description("Servidor de desenvolvimento"));
    }


}
