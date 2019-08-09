/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-09T22:14:50.208+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafkaproducer;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@EnableBinding({Source.class, JsonBinding.class, ProducerChannel.class})
public class ProducerController {

    private Source source;
    private KafkaService service;
    private ProducerChannel producer;
    private List<String> messages = Lists.newArrayList();


    public ProducerController() {
        for (int i = 0; i < 10; i++) {
            messages.add(RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(1, 10)));
        }
    }


    @RequestMapping(value = "/send/{msg}", method = RequestMethod.GET)
    public void send(@PathVariable("msg") String msg) {
        service.sendMessage(msg, UUID.randomUUID().toString(), source.output());
        service.sendMessageObj(msg);
    }


    @RequestMapping(value = "/wordCount", method = RequestMethod.GET)
    public void wordCount() {
        for (int i = 0; i < 50; i++) {
            service.sendMessageWithUUID(messages.get(RandomUtils.nextInt(0, messages.size())));
        }
    }


    @RequestMapping(value = "/branch", method = RequestMethod.GET)
    public void branch() {

        List<String> keys = Lists.newArrayList("1", "2", "3");
        for (int i = 0; i < 50; i++) {
            service.sendMessage(messages.get(RandomUtils.nextInt(0, messages.size())),
                                keys.get(RandomUtils.nextInt(0, 3)),
                                producer.branch());
        }
    }

    @Autowired
    public void setService(KafkaService service) {
        this.service = service;
    }

    @Autowired
    public void setProducer(ProducerChannel producer) {
        this.producer = producer;
    }

    @Autowired
    public void setSource(Source source) {
        this.source = source;
    }
}
