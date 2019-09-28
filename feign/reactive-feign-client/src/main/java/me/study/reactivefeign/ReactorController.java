/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-28T23:01:12.953+08:00
 * LGPL licence
 *
 */

package me.study.reactivefeign;

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
}
