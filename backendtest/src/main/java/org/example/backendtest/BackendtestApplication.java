package org.example.backendtest;

import org.example.backendtest.entities.User;
import org.example.backendtest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendtestApplication {

    @Autowired
    private UserService userService;  // Injecter UserService au lieu de UserRepository

      // Injecter PasswordEncoder pour encoder le mot de passe

    public static void main(String[] args) {
        SpringApplication.run(BackendtestApplication.class, args);
    }



    @Bean
    public PasswordEncoder customPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
