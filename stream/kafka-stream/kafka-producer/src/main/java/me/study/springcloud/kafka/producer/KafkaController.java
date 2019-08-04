/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-04T16:56:03.748+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafka.producer;

import me.study.springcloud.Address;
import me.study.springcloud.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    private KafkaService service;

    @GetMapping(path = "send/{message}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMessage(@PathVariable("message") String message) {
        User result = User.newBuilder()
                          .setName(message)
                          .setFavoriteColor(RandomStringUtils.randomAlphanumeric(10))
                          .setFavoriteNumber(RandomUtils.nextInt(0, 100))
                          .setAddress(Address.newBuilder().setName(RandomStringUtils.randomAlphanumeric(10)).build())
                          .build();

        service.sendMessage(result);
        return ResponseEntity.ok(result.toString());
    }


    @Autowired
    public void setService(KafkaService service) {
        this.service = service;
    }


}
