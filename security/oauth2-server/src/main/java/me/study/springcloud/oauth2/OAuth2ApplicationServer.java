/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-23T23:56:18.440+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@EnableAuthorizationServer
@SpringBootApplication
public class OAuth2ApplicationServer {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ApplicationServer.class, args);
    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("{bcrypt}$2a$10$cNf9lwLAWFI.K0g3YbESzuOZ8GqABia39qJbMVo468OmR.JmtohzS").roles("USER").build());
        return manager;
    }

    @RestController
    class UserController {

        @GetMapping({"/user", "/me"})
        public Principal user(Principal user) {
            return user;
        }

    }

}
