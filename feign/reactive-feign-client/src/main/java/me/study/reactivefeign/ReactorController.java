/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-29T23:03:00.313+08:00
 * LGPL licence
 *
 */

package me.study.reactivefeign;

import me.study.springcloud.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ReactorController {

    @Autowired
    private ReactiveFeignService service;

    @GetMapping("/greeting")
    public Mono<String> greeting() {
        return service.greeting();
    }

    @GetMapping("/greetingAvro")
    public Mono<String> greetingAvro() {
        return service.greetingAvro().map(Address::getName);
    }
}
