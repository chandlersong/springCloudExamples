/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-10-01T21:16:36.808+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.services.ok;

import lombok.extern.slf4j.Slf4j;
import me.study.springcloud.Address;
import me.study.springcloud.User;
import me.study.springcloud.io.AvroMediaType;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootApplication
@RestController
@Configuration
public class ReactiveOKServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveOKServiceApplication.class);
    }

    @GetMapping("/greeting")
    public Mono<String> greeting() {
        return Mono.just(String.format("Hello from '%s'!", RandomStringUtils.randomAlphabetic(10)));
    }

    @GetMapping(value = "/greetingAvro", produces = AvroMediaType.AVRO_BINARY_VALUE)
    public Mono<Address> greetingAvro() {
        log.info("greetingAvro executed");
        return Mono.just(new Address(RandomStringUtils.randomAlphabetic(10)));
    }

    @GetMapping(value = "/greetingFlux", produces = AvroMediaType.AVRO_BINARY_VALUE)
    public Flux<Address> greetingAvroFlux() {

        return Flux.fromArray(new Address[]{
                new Address(RandomStringUtils.randomAlphabetic(10)),
                new Address(RandomStringUtils.randomAlphabetic(10)),
                new Address(RandomStringUtils.randomAlphabetic(10))
        });
    }

    @PostMapping(value = "/testAvro", consumes = AvroMediaType.AVRO_BINARY_VALUE, produces = AvroMediaType.AVRO_BINARY_VALUE)
    public Mono<Address> testAvro(@RequestBody User user) {
        log.info("receive user name:{}", user.getName());
        return Mono.just(user.getAddress());
    }
}
