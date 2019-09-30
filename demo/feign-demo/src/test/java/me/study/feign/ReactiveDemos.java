/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-30T21:52:06.649+08:00
 * LGPL licence
 *
 */

package me.study.feign;

import lombok.extern.slf4j.Slf4j;
import me.demo.springcloud.utils.AvroWebClient;
import me.demo.springcloud.utils.RestRequest;
import me.demo.springcloud.utils.ServerRunner;
import me.study.reactivefeign.ReactiveFeignApplication;
import me.study.springcloud.Address;
import me.study.springcloud.User;
import me.study.springcloud.eureka.server.EurekaServerApplication;
import me.study.springcloud.services.ok.ReactiveOKServiceApplication;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
public class ReactiveDemos {


    private WebClient binaryClient;

    @Test
    public void testHelloWorld() throws InterruptedException {
        log.info("server port:{}", "8080");

        startServer();
        //log.info("request get:,{}", RestRequest.get("/greeting"));
        log.info("request get:,{}", RestRequest.get("/greetingAvro"));
        log.info("stop");
    }


    @Test
    public void testAvro() {

//        ServerRunner.createAndRunServer(ReactiveOKServiceApplication.class,
//                                        "reactive/reactive_ok_services_client_without_discovery.yml");

        User user = createUser();

        Mono<Address> data = binaryClient
                .method(HttpMethod.POST)
                .uri("/testAvro")
                .body(BodyInserters.fromObject(user))
                .retrieve()
                .bodyToMono(Address.class);

        log.info("send: {}", user);
        log.info("webclient result,{}", Objects.requireNonNull(data.block()).getName());
        log.info("stop");
    }

    private void startServer() throws InterruptedException {
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "simplest_eureka_server.yml");
        ServerRunner.createAndRunServer(ReactiveFeignApplication.class, "reactive/application.yml");
        ServerRunner.createAndRunServer(ReactiveOKServiceApplication.class, "reactive/reactive_ok_services_client.yml");
        Thread.sleep(80 * 1000);
    }

    @Before
    public void setup() {
        binaryClient = AvroWebClient.createAvroWebClient("http://localhost:8080");
    }

    private User createUser() {
        return User.newBuilder()
                .setName(RandomStringUtils.randomAlphanumeric(10))
                .setFavoriteColor(RandomStringUtils.randomAlphanumeric(10))
                .setFavoriteNumber(RandomUtils.nextInt(0, 100))
                .setAddress(Address.newBuilder().setName(RandomStringUtils.randomAlphanumeric(10)).build())
                .build();
    }
}
