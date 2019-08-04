/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-04T16:56:03.768+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class KafkaStreamProducerApplication {

    private KafkaService service;

    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamProducerApplication.class);
    }

    @RequestMapping(value = "/send/{msg}", method = RequestMethod.GET)
    public void send(@PathVariable("msg") String msg) {
        service.sendMessage(msg);
        service.sendMessageObj(msg);
    }

    @Autowired

    public void setService(KafkaService service) {
        this.service = service;
    }
}
