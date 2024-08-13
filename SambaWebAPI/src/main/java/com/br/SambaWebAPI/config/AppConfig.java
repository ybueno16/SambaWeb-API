package com.br.SambaWebAPI.config;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.adapter.impl.ProcessBuilderAdapterImpl;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.user.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public ProcessBuilder processBuilder() {
    return new ProcessBuilder();
  }

  @Bean
  public ProcessBuilderAdapter processBuilderAdapter() {
    return new ProcessBuilderAdapterImpl();
  }

  @Bean
  public Global global() {
    return new Global();
  }

  @Bean
  public CommandLineRunner commandLineRunner() {
    return args -> {
      if (args.length < 2) {
        System.out.println("Erro: número insuficiente de argumentos");
        System.out.println("Uso: java Main user=sambauser sudoAuthentication=123");
        System.exit(1);
      }

      // Crie objetos User e SudoAuthentication com os argumentos
      User user = new User(args[0].split("=")[1]);
      SudoAuthentication sudoAuthentication = new SudoAuthentication(args[1].split("=")[1]);
      ProcessBuilderAdapter processBuilderAdapter = new ProcessBuilderAdapterImpl();

      // Crie uma instância da classe que contém o método createUser
      UserService userService = new UserService(processBuilderAdapter);


      // Chame o método createUser com os objetos criados
      try {
        userService.createUser(user, sudoAuthentication);
      } catch (Exception e) {
        System.out.println("Erro ao criar usuário: " + e.getMessage());
      }
    };
  }
}