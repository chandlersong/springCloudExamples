/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-10-01T21:16:36.811+08:00
 * LGPL licence
 *
 */

package me.study.reactivefeign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class ReactorController {

    @Autowired
    private ReactiveFeignService service;

    @GetMapping("/greeting")
    public Mono<String> greeting() {
        return service.greeting();
    }

    @GetMapping(value = "/greetingAvro")
    public Mono<String> greetingAvro() {
        return Mono.just(service.greetingAvro().getName());
    }
}
