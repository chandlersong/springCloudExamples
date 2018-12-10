/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-12-10T23:37:47.791+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
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

    @Bean
    public KeyPair generateKeyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
        return keyStoreKeyFactory.getKeyPair("mytest");
    }
}
