/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-28T23:01:12.939+08:00
 * LGPL licence
 *
 */

package me.study.reactivefeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication(exclude = ReactiveLoadBalancerAutoConfiguration.class)
@EnableEurekaClient
@EnableReactiveFeignClients
public class ReactiveFeignApplication {


    public static void main(String[] args) {
        SpringApplication.run(ReactiveFeignApplication.class);
    }

}
