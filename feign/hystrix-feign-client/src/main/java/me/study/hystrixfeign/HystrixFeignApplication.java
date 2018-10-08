/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-08T18:39:01.913+08:00
 * LGPL licence
 *
 */

package me.study.hystrixfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HystrixFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixFeignApplication.class, args);
    }
}
