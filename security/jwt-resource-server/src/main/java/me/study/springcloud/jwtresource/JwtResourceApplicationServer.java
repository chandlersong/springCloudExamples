/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-12-06T22:48:04.136+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.jwtresource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@SpringBootApplication
public class JwtResourceApplicationServer {
    public static void main(String[] args) {
        SpringApplication.run(JwtResourceApplicationServer.class, args);
    }

}
