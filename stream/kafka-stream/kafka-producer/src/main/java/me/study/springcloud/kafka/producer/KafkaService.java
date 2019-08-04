/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-04T16:56:03.751+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafka.producer;


import me.study.springcloud.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableBinding({Source.class})
public class KafkaService {

    private Source source;


    public void sendMessage(User user) {
        try {
            Message<User> message = MessageBuilder.withPayload(user)
                                                  .build();
            source.output().send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setSource(Source source) {
        this.source = source;
    }
}
