package com.br.SambaWebAPI;

import com.br.SambaWebAPI.adapter.impl.ProcessBuilderAdapterImpl;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.models.User;
import com.br.SambaWebAPI.user.services.UserService;
import java.io.IOException;
import java.util.Properties;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@SpringBootApplication
public class SambaWebApiApplication {

  public static void main(String[] args) throws IOException, FlywayException {
    Properties props =
        PropertiesLoaderUtils.loadProperties(new ClassPathResource("application.properties"));

    String ip = props.getProperty("postgres.ip");
    String port = props.getProperty("postgres.port");
    String driver = props.getProperty("postgres.driver");
    String database = props.getProperty("postgres.database");
    String username = props.getProperty("postgres.username");
    String password = props.getProperty("postgres.password");

    Flyway flyway =
        Flyway.configure()
            .dataSource(driver + "://" + ip + ":" + port + "/" + database, username, password)
            .locations("classpath:db/migrations")
            .schemas("public")
            .table("flyway_schema_history")
            .baselineOnMigrate(true)
            .load();
    try {
      flyway.migrate();
      System.out.println("Migration successful!");
    } catch (FlywayException e) {
      System.err.println("Migration failed: " + e.getMessage());
    }

    if (args.length > 2) {
      System.err.println(
          "Erro: número insuficiente de parâmetros. Para criar um usuário, "
              + "execute o sistema com os seguintes parâmetros:");
      System.err.println("./gradlew bootRun --args=\"<NomeUsuario> <SenhaSudo>\"");
    } else {
      SpringApplication.run(SambaWebApiApplication.class, args);
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
