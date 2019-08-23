/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-23T21:40:31.545+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.stream.kafka;

import lombok.extern.slf4j.Slf4j;
import me.demo.springcloud.utils.RestTemplateWrapper;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.kafka.custombing.KafkaCustomBindingApplication;
import me.study.springcloud.kafkacomsumer.KafkaStreamConsumerApplication;
import me.study.springcloud.kafkaproducer.KafkaStreamProducerApplication;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class HelloWorldTests {

    private RestTemplateWrapper produceTemplate = new RestTemplateWrapper(18081);
    private RestTemplateWrapper customerTemplate = new RestTemplateWrapper(18082);

    @Test
    public void sendAndReceive() {
        ServerRunner.createAndRunServer(KafkaStreamProducerApplication.class, "simple/application_simple-produce.yml");
        ServerRunner.createAndRunServer(KafkaStreamConsumerApplication.class, "simple/application_simple-consumer.yml");


        String key = RandomStringUtils.randomAlphabetic(10);
        produceTemplate.doGet("send/" + key);
        log.info("return value:{} ", key);
        log.info("stop");
    }

    @Test
    public void testCustomBindingAndProcessor() {
        ServerRunner.createAndRunServer(KafkaStreamProducerApplication.class,
                                        "custombinding/application_simple-produce.yml");
        ServerRunner.createAndRunServer(KafkaCustomBindingApplication.class,
                                        "custombinding/application_simple-custom.yml");
        ServerRunner.createAndRunServer(KafkaStreamConsumerApplication.class,
                                        "custombinding/application_simple-consumer.yml");


        String key = RandomStringUtils.randomAlphabetic(10);
        produceTemplate.doGet("send/" + key);
        log.info("return value:{} ", key);
        log.info("stop");
    }

}
