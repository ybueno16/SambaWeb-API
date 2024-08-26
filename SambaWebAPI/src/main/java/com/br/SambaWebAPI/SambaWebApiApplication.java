package com.br.SambaWebAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SambaWebApiApplication {

  public static void main(String[] args) {
    //    if (args.length != 2) {
    //      System.err.println("Erro: número insuficiente de parâmetros. Para criar um usuário,
    // execute o sistema com os seguintes parâmetros:");
    //      System.err.println("  ./gradlew bootRun --args=\"<NomeUsuario> <SenhaSudo>\"");
    //      return;
    //    }
    //
    //    String userName = args[0];
    //    String sudoPassword = args[1];
    //
    //    try {
    //      UserService userService = new UserService(new ProcessBuilderAdapterImpl());
    //      User user = new User(userName);
    //      SudoAuthentication sudoAuthentication = new SudoAuthentication(sudoPassword);
    //      userService.createUser(user, sudoAuthentication);
    //    } catch (Exception e) {
    //      System.err.println("Erro: " + e.getMessage());
    //    }

    SpringApplication.run(SambaWebApiApplication.class, args);
  }
}
