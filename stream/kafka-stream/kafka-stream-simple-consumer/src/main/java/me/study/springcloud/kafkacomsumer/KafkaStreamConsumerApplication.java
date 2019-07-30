/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-07-31T06:21:44.347+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafkacomsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

@Slf4j
@EnableBinding(Sink.class)
@SpringBootApplication
public class KafkaStreamConsumerApplication {


    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamConsumerApplication.class);
    }


    @StreamListener(Sink.INPUT)
    public void process(Message<?> message) {
        log.info(message.getPayload().toString());
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            log.info("Acknowledgment provided");
            acknowledgment.acknowledge();
        }

        Object o = message.getHeaders().get(KafkaHeaders.OFFSET, Long.class);

        log.info("offset is {}", o);
    }

}
