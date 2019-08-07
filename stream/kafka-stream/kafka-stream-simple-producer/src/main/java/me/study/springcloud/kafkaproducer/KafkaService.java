/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-08T00:03:01.032+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafkaproducer;

import me.study.springcloud.entry.Company;
import me.study.springcloud.entry.CompanyA;
import me.study.springcloud.entry.CompanyB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@EnableBinding({Source.class, JsonBinding.class})
public class KafkaService {

    private Source source;

    private JsonBinding json;

    public void sendMessage(String msg) {
        try {

            source.output().send(MessageBuilder.withPayload(msg)
                    .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendMessageObj(String msg) {
        try {
            json.sendChannel().send(MessageBuilder.withPayload(new Company(msg)).build());
            json.sendChannel().send(MessageBuilder.withPayload(new CompanyA(msg)).build());
            json.sendChannel().send(MessageBuilder.withPayload(new CompanyB(msg)).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setSource(Source source) {
        this.source = source;
    }

    @Autowired
    public void setJson(JsonBinding json) {
        this.json = json;
    }
}
