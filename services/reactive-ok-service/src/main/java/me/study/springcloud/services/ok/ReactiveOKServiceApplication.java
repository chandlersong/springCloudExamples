/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-28T23:01:12.949+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.services.ok;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class ReactiveOKServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveOKServiceApplication.class);
    }


    @GetMapping("/greeting")
    public Mono<String> greeting() {
        return Mono.just(String.format("Hello from '%s'!", RandomStringUtils.randomAlphabetic(10)));
    }


}
