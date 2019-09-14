/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-14T14:20:48.711+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.miscellanous;

import lombok.extern.slf4j.Slf4j;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.Address;
import me.study.springcloud.User;
import me.study.springcloud.io.AvroMessageConverter;
import me.study.springcloud.services.ok.OKServicesApplication;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static me.study.springcloud.io.AvroMediaType.AVRO_BINARY;

@Slf4j
public class AvroTests {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testUseAvroBinary() {
        ServerRunner.createAndRunServer(OKServicesApplication.class, "avro/ok_services.yml");

        User user = User.newBuilder()
                .setName(RandomStringUtils.randomAlphanumeric(10))
                .setFavoriteColor(RandomStringUtils.randomAlphanumeric(10))
                .setFavoriteNumber(RandomUtils.nextInt(0, 100))
                .setAddress(Address.newBuilder().setName(RandomStringUtils.randomAlphanumeric(10)).build())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(AVRO_BINARY));
        headers.setContentType(AVRO_BINARY);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<Address> result =
                restTemplate.exchange("http://localhost:8080/avroBinary", HttpMethod.POST, entity, Address.class);

        log.info("stop");
    }


    @Before
    public void setup() {
        restTemplate.getMessageConverters().add(0, new AvroMessageConverter<>(
                true,
                AVRO_BINARY));
    }
}
