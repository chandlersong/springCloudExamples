/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-07-29T23:22:54.234+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.stream.kafkaconsumer;

import lombok.extern.slf4j.Slf4j;
import me.study.springcloud.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

@Slf4j
@EnableBinding(Sink.class)
@SpringBootApplication
@Import(ConsumerConfiguration.class)
public class KafkaConsumerApplication {

    @StreamListener(Sink.INPUT)
    public void process(Message<User> message) {
        log.info(message.getPayload().toString());
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            log.info("Acknowledgment provided");
            acknowledgment.acknowledge();
        }
    }
}
