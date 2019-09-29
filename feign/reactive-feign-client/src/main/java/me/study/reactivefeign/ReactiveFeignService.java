/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-29T23:03:00.309+08:00
 * LGPL licence
 *
 */

package me.study.reactivefeign;

import me.study.springcloud.Address;
import me.study.springcloud.io.AvroMediaType;
import org.springframework.web.bind.annotation.GetMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "${reactive.remote:ReactiveOKService}")
public interface ReactiveFeignService {

    @GetMapping("/greeting")
    Mono<String> greeting();

    @GetMapping(value = "/greetingAvro", produces = AvroMediaType.AVRO_BINARY_VALUE)
    Mono<Address> greetingAvro();
}
