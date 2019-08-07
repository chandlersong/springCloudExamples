/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-08T00:03:01.037+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafkaproducer;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


    @RequestMapping(value = "/wordCount", method = RequestMethod.GET)
    public void wordCount() {

        List<String> msgs = Lists.newArrayList("hello",
                "I",
                "am",
                "good",
                "man",
                "test",
                "is",
                "ok",
                "abc",
                "world",
                "bad");


        for (int i = 0; i < 50; i++) {

            service.sendMessage(msgs.get(RandomUtils.nextInt(0, msgs.size())));
        }

    }

    @Autowired

    public void setService(KafkaService service) {
        this.service = service;
    }
}
