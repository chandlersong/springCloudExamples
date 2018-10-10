/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-11T00:00:57.639+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.config;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import static org.slf4j.LoggerFactory.getLogger;

@SpringBootApplication
@EnableConfigServer
public class SimpleConfigServer {

    private static final Logger logger = getLogger(SimpleConfigServer.class);

    public static void main(String[] args) {
        SpringApplication.run(SimpleConfigServer.class, args);
        logger.info("spring-cloud-config-serve start!");
    }
}
