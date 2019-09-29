/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-29T23:03:00.305+08:00
 * LGPL licence
 *
 */

package me.study.feign;

import lombok.extern.slf4j.Slf4j;
import me.demo.springcloud.utils.RestRequest;
import me.demo.springcloud.utils.ServerRunner;
import me.study.reactivefeign.ReactiveFeignApplication;
import me.study.springcloud.Address;
import me.study.springcloud.User;
import me.study.springcloud.eureka.server.EurekaServerApplication;
import me.study.springcloud.io.AvroMessageArrayConverter;
import me.study.springcloud.io.AvroMessageConverter;
import me.study.springcloud.services.ok.ReactiveOKServiceApplication;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static me.study.springcloud.io.AvroMediaType.AVRO_BINARY;

@Slf4j
public class ReactiveDemos {

    private RestTemplate binaryTemplate = new RestTemplate();

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

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(AVRO_BINARY));
        headers.setContentType(AVRO_BINARY);
        HttpEntity<User> entity = new HttpEntity<>(createUser(), headers);

        ResponseEntity<Address> result =
                binaryTemplate.exchange("http://localhost:8080/testAvro", HttpMethod.POST, entity, Address.class);
        log.info("status code:,{}", result.getStatusCode().value());
        log.info("request get:,{}", Objects.requireNonNull(result.getBody()).getName());
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
        List<HttpMessageConverter<?>> messageConverters = binaryTemplate.getMessageConverters();
        messageConverters.add(0, new AvroMessageArrayConverter<>(true, AVRO_BINARY));
        messageConverters.add(1, new AvroMessageConverter<>(true, AVRO_BINARY));
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
