/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-10-08T21:55:09.605+08:00
 * LGPL licence
 *
 */

package me.study.reactivefeign;

import lombok.extern.slf4j.Slf4j;
import me.study.springcloud.Address;
import me.study.springcloud.io.AvroMediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class ReactorController {

    @Autowired
    private WebClient.Builder webClientBuilder;

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

    @GetMapping(value = "/greetingWebClient", produces = AvroMediaType.AVRO_BINARY_VALUE)
    public Mono<Address> greetingWebClient() {
        return webClientBuilder.build().get().uri("/greetingAvro").retrieve().bodyToMono(Address.class);
    }

    @GetMapping(value = "/greetingError", produces = AvroMediaType.AVRO_BINARY_VALUE)
    public Mono<Address> greetingWebClientError() {
        return webClientBuilder.build()
                               .get()
                               .uri("/testAvroError")
                               .retrieve()
                               .bodyToMono(Address.class)
                               .doOnError(e -> log.warn("error!", e));
    }
}
