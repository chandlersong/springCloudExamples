/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-10-01T22:29:36.536+08:00
 * LGPL licence
 *
 */

package me.study.reactivefeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancerAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = ReactiveLoadBalancerAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
@Configuration
public class ReactiveFeignApplication {


    public static void main(String[] args) {
        SpringApplication.run(ReactiveFeignApplication.class);
    }


}
