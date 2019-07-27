/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-07-27T16:17:40.288+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafka.custombing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageController {

    private CustomerBinding binding;

    @GetMapping("/send/{message}")
    public void sendMessage(@PathVariable("message") String message) {
        try {
            binding.anOutput().send(MessageBuilder.withPayload(message).build());
        } catch (Exception e) {
            log.error("error happened when sending message", e);
        }
    }


    @StreamListener(CustomerBinding.INPUT)
    @SendTo(CustomerBinding.OUTPUT)
    public String process(Message<?> message) {
        String msg = message.getPayload().toString();
        log.info(msg);
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            log.info("Acknowledgment provided");
            acknowledgment.acknowledge();
        }

        return msg;
    }


    @Autowired
    public void setBinding(CustomerBinding binding) {
        this.binding = binding;
    }
}
