/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-10-01T21:16:36.804+08:00
 * LGPL licence
 *
 */

package me.study.reactivefeign;

import me.study.springcloud.Address;
import me.study.springcloud.io.AvroMediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@FeignClient(name = "${reactive.remote:ReactiveOKService}", fallback = FeignOkServicesFallBack.class)
public interface ReactiveFeignService {

    @GetMapping("/greeting")
    Mono<String> greeting();

    @GetMapping(value = "/greetingAvro", produces = AvroMediaType.AVRO_BINARY_VALUE)
    Address greetingAvro();


}
