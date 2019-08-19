/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-19T22:22:39.531+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.stream.kafka;

import lombok.extern.slf4j.Slf4j;
import me.demo.springcloud.utils.RestTemplateWrapper;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.kafka.stream.wordcount.WordCountApplication;
import me.study.springcloud.kafkacomsumer.KafkaStreamConsumerApplication;
import me.study.springcloud.kafkaproducer.KafkaStreamProducerApplication;
import me.study.springcloud.stream.branch.SimpleStreamBranchApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class StreamDemo {

    private RestTemplateWrapper produceTemplate = new RestTemplateWrapper(18081);
    private RestTemplateWrapper processTemplate = new RestTemplateWrapper(18082);

    @Test
    public void testWorkCount() {
        ServerRunner.createAndRunServer(KafkaStreamProducerApplication.class,
                                        "wordcount/application_simple-producer.yml");
        ServerRunner.createAndRunServer(WordCountApplication.class,
                                        "wordcount/application_simple-processor.yml");
        ServerRunner.createAndRunServer(KafkaStreamConsumerApplication.class,
                                        "wordcount/application_simple-consumer.yml");


        produceTemplate.doGet("/wordCount");
        log.info("stop");
    }


    @Test
    public void testBranch() {
        ServerRunner.createAndRunServer(KafkaStreamProducerApplication.class,
                                        "simplebranch/application_simple-producer.yml");
        ServerRunner.createAndRunServer(SimpleStreamBranchApplication.class,
                                        "simplebranch/application_simple-processor.yml");
        ServerRunner.createAndRunServer(KafkaStreamConsumerApplication.class,
                                        "simplebranch/application_simple-consumer.yml");


        produceTemplate.doGet("/branch");
        log.info("stop");
    }


    @Test
    public void testInteractiveQuery() throws InterruptedException {
        ServerRunner.createAndRunServer(KafkaStreamProducerApplication.class,
                                        "simplebranch/application_simple-producer.yml");
        ServerRunner.createAndRunServer(SimpleStreamBranchApplication.class,
                                        "simplebranch/application_simple-processor.yml");


        produceTemplate.doGet("/InteractiveQuery");
        Thread.sleep(5 * 1000);
        processTemplate.doGet("/InteractiveQuery");

        log.info("stop");
    }
}
