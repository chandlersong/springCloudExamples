/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-10-01T21:16:36.786+08:00
 * LGPL licence
 *
 */

package me.study.reactivefeign;

import me.study.springcloud.Address;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class FeignOkServicesFallBack implements ReactiveFeignService {
    @Override
    public Mono<String> greeting() {
        return Mono.just("fallback");
    }

    @Override
    public Address greetingAvro() {
        return new Address("fallback");
    }
}
