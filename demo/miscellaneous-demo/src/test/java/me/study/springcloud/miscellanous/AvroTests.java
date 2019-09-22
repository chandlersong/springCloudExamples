/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-22T12:52:17.041+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.miscellanous;

import lombok.extern.slf4j.Slf4j;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.Address;
import me.study.springcloud.User;
import me.study.springcloud.io.AvroMessageConverter;
import me.study.springcloud.io.AvroMessageListConverter;
import me.study.springcloud.services.ok.OKServicesApplication;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
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
import static me.study.springcloud.io.AvroMediaType.AVRO_JSON;

@Slf4j
public class AvroTests {

    private RestTemplate binaryTemplate = new RestTemplate();
    private RestTemplate jsonTemplate = new RestTemplate();

    @Test
    public void testUseAvroBinary() {
        ServerRunner.createAndRunServer(OKServicesApplication.class, "avro/ok_services.yml");

        User user = createUser();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(AVRO_BINARY));
        headers.setContentType(AVRO_BINARY);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<Address> result =
                binaryTemplate.exchange("http://localhost:8081/avroBinary", HttpMethod.POST, entity, Address.class);

        Assert.assertEquals(user.getAddress().getName(), Objects.requireNonNull(result.getBody()).getName());

        log.info("stop");
    }


    @Test
    public void testUseAvroBinaryList() {
        ServerRunner.createAndRunServer(OKServicesApplication.class, "avro/ok_services.yml");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(AVRO_BINARY));
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<Address> result =
                binaryTemplate.exchange("http://localhost:8080/avroList", HttpMethod.GET, entity, Address.class);


        log.info("stop");
    }


    /**
     * request json would be
     * {
     * "name": "Gr2qLmypLA",
     * "favorite_number": {
     * "int": 77
     * },
     * "favorite_color": {
     * "string": "bTADsRvqad"
     * },
     * "address": {
     * "me.study.springcloud.Address": {
     * "name": "YARbMfTdzi"
     * }
     * }
     * }
     */
    @Test
    public void testUseAvroJson() {
        ServerRunner.createAndRunServer(OKServicesApplication.class, "avro/ok_services.yml");
        User user = createUser();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(AVRO_JSON));
        headers.setContentType(AVRO_JSON);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<Address> result =
                jsonTemplate.exchange("http://localhost:8081/avroJson", HttpMethod.POST, entity, Address.class);

        Assert.assertEquals(user.getAddress().getName(), Objects.requireNonNull(result.getBody()).getName());

        log.info("stop");
    }

    private User createUser() {
        return User.newBuilder()
                .setName(RandomStringUtils.randomAlphanumeric(10))
                .setFavoriteColor(RandomStringUtils.randomAlphanumeric(10))
                .setFavoriteNumber(RandomUtils.nextInt(0, 100))
                .setAddress(Address.newBuilder().setName(RandomStringUtils.randomAlphanumeric(10)).build())
                .build();
    }


    @Before
    public void setup() {
        List<HttpMessageConverter<?>> messageConverters = binaryTemplate.getMessageConverters();
        messageConverters.add(0, new AvroMessageListConverter(true, AVRO_BINARY));
        messageConverters.add(1, new AvroMessageConverter<>(true, AVRO_BINARY));
        jsonTemplate.getMessageConverters().add(0, new AvroMessageConverter<>(false, AVRO_JSON));
    }
}
