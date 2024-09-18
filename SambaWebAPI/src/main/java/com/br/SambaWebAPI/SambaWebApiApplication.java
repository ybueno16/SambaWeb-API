package com.br.SambaWebAPI;

import com.br.SambaWebAPI.adapter.impl.ProcessBuilderAdapterImpl;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.user.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SambaWebApiApplication {


  public static void main(String[] args) {
    
        if (args.length > 2) {
          System.err.println("Erro: número insuficiente de parâmetros. Para criar um usuário, " +
                  "execute o sistema com os seguintes parâmetros:");
          System.err.println("  ./gradlew bootRun --args=\"<NomeUsuario> <SenhaSudo>\"");
          return;
        }

        String userName = args[0];
        String sudoPassword = args[1];

        try {
          UserService userService = new UserService(new ProcessBuilderAdapterImpl());
          User user = new User();
          user.setUser(userName);
          SudoAuthentication sudoAuthentication = new SudoAuthentication();
          sudoAuthentication.setSudoPassword(sudoPassword);
          userService.createUser(user, sudoAuthentication);
        } catch (Exception e) {
          System.err.println("Erro: " + e.getMessage());
        }

    SpringApplication.run(SambaWebApiApplication.class, args);
  }
}
