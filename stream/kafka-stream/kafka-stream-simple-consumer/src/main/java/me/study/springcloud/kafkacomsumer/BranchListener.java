/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-09T00:13:37.487+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafkacomsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(ListenerBinding.class)
public class BranchListener {

    @StreamListener(ListenerBinding.BRANCH_1)
    public void branch1(Message<?> message) {
        log.info("message from branch1,{}", message.getPayload().toString());
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            log.info("Acknowledgment provided");
            acknowledgment.acknowledge();
        }
    }

    @StreamListener(ListenerBinding.BRANCH_2)
    public void branch2(Message<?> message) {
        log.info("message from branch2,{}", message.getPayload().toString());
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            log.info("Acknowledgment provided");
            acknowledgment.acknowledge();
        }
    }


    @StreamListener(ListenerBinding.BRANCH_3)
    public void branch3(Message<?> message) {
        log.info("message from branch3,{}", message.getPayload().toString());
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            log.info("Acknowledgment provided");
            acknowledgment.acknowledge();
        }
    }

}
