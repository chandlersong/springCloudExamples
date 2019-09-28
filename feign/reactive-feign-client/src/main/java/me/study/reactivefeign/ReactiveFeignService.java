/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-28T23:01:12.944+08:00
 * LGPL licence
 *
 */

package me.study.reactivefeign;

import org.springframework.web.bind.annotation.GetMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "${reactive.remote:ReactiveOKService}")
public interface ReactiveFeignService {

    @GetMapping("/greeting")
    Mono<String> greeting();
}
