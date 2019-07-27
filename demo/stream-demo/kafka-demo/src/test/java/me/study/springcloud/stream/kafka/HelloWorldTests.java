/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-07-27T11:26:10.675+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.stream.kafka;

import lombok.extern.slf4j.Slf4j;
import me.demo.springcloud.utils.RestTemplateWrapper;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.kafkacomsumer.KafkaStreamConsumerApplication;
import me.study.springcloud.kafkaproducer.KafkaStreamProducerApplication;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class HelloWorldTests {

    private RestTemplateWrapper template = new RestTemplateWrapper(18081);

    @Test
    public void sendAndReceive() {
        ServerRunner.createAndRunServer(KafkaStreamProducerApplication.class, "application_simple-produce.yml");
        ServerRunner.createAndRunServer(KafkaStreamConsumerApplication.class, "application_simple-consumer.yml");


        String key = RandomStringUtils.randomAlphabetic(10);
        template.doGet("send/" + key);
        log.info("return value:{} ", key);
        log.info("stop");
    }
}
