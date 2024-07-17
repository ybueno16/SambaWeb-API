//package com.br.SambaWebAPI.password.services;
//
//import com.br.SambaWebAPI.SambaWebApiApplication;
//import com.br.SambaWebAPI.password.models.SudoAuthentication;
//import com.br.SambaWebAPI.user.models.User;
//import com.br.SambaWebAPI.user.services.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = SambaWebApiApplication.class)
//class PasswordServiceTest {
//
//    @Autowired
//    UserService userService;
//    @Autowired
//    PasswordService passwordService;
//
//    @BeforeEach
//    public void tearDown() throws Exception {
//        User user = new User();
//        SudoAuthentication sudoAuthentication = new SudoAuthentication();
//        sudoAuthentication.setSudoPassword("senhaforte123");
//        user.setUser("sambauser");
//        if (userService.getUser(user)) {
//            try {
//                userService.removeUser(user, sudoAuthentication);
//            }catch (Exception ignored){
//
//            }
//        }
//    }
//    @Test
//    void createPassword() throws Exception {
//        User user = new User();
//        SudoAuthentication sudoAuthentication = new SudoAuthentication();
//        sudoAuthentication.setSudoPassword("senhaforte123");
//        user.setUser("sambauser");
//        boolean success = passwordService.createPassword(user,sudoAuthentication);
//        assertTrue(success);
//    }
//}