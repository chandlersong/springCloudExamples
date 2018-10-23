/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-23T23:56:18.432+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.oauth2.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@SpringBootApplication
public class ResourceApplicationServer {

    public static void main(String[] args) {
        SpringApplication.run(ResourceApplicationServer.class, args);
    }
}
