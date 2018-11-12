/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-11-12T23:14:26.341+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.HashMap;
import java.util.Map;

@EnableAuthorizationServer
@SpringBootApplication
public class JwtApplicationServer {

    public static void main(String[] args) {
        SpringApplication.run(JwtApplicationServer.class, args);
    }


    @Bean
    public PasswordEncoder createPasswordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }

    /**
     * it's seems AuthenticationManager need the user DetailsSevers
     *
     * @return UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("{bcrypt}$2a$10$ETDrFt.TcsQ5AY.59kb8/OUxqBatlkNZd1XuJRwYh7Toz8fAcvHzW").roles("USER").build());
        return manager;
    }
}
