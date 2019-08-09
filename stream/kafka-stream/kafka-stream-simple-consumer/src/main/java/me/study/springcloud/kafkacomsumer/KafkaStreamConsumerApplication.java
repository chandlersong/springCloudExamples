/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-09T22:14:50.205+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafkacomsumer;

import lombok.extern.slf4j.Slf4j;
import me.study.springcloud.entry.Company;
import me.study.springcloud.entry.CompanyA;
import me.study.springcloud.entry.CompanyB;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

@Slf4j
@EnableBinding({Sink.class, JsonBinding.class})
@SpringBootApplication
public class KafkaStreamConsumerApplication {


    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamConsumerApplication.class);
    }


    @StreamListener(Sink.INPUT)
    public void process(Message<?> message) {
        log.info("word count hello world:{}", message.getPayload().toString());
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);


        if (acknowledgment != null) {
            log.info("Acknowledgment provided");
            acknowledgment.acknowledge();
        }

        Object o = message.getHeaders().get(KafkaHeaders.OFFSET, Long.class);

        Consumer<?, ?> consumer = message.getHeaders().get(KafkaHeaders.CONSUMER, Consumer.class);
        assert consumer != null;
        consumer.commitSync();
        log.info("offset is {}", o);
    }

    @StreamListener(JsonBinding.JSON_CONSUMER)
    public void processJson(Message<Company> message) {
        log.info("company is {}", message.getPayload());
    }

    @StreamListener(JsonBinding.JSON_CONSUMER)
    public void processJson(CompanyA message) {
        log.info("company A is {}", message.getName());
    }

    @StreamListener(JsonBinding.JSON_CONSUMER)
    public void processJson(CompanyB message) {
        log.info("company B is {}", message.getName());
    }

}
