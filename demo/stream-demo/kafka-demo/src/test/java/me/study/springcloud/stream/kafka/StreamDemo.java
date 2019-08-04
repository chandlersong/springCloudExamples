/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-04T22:02:52.936+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.stream.kafka;

import lombok.extern.slf4j.Slf4j;
import me.demo.springcloud.utils.RestTemplateWrapper;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.kafka.stream.KafkaStreamProcessorApplication;
import me.study.springcloud.kafkacomsumer.KafkaStreamConsumerApplication;
import me.study.springcloud.kafkaproducer.KafkaStreamProducerApplication;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class StreamDemo {

    private RestTemplateWrapper produceTemplate = new RestTemplateWrapper(18081);

    @Test
    public void testWorkCount() {
        ServerRunner.createAndRunServer(KafkaStreamProducerApplication.class,
                                        "wordcount/application_simple-producer.yml");
        ServerRunner.createAndRunServer(KafkaStreamProcessorApplication.class,
                                        "wordcount/application_simple-processor.yml");
        ServerRunner.createAndRunServer(KafkaStreamConsumerApplication.class,
                                        "wordcount/application_simple-consumer.yml");


        String key = RandomStringUtils.randomAlphabetic(10);
        produceTemplate.doGet("send/" + key);
        log.info("return value:{} ", key);
        log.info("stop");
    }
}
