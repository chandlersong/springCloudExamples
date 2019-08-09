/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-09T23:24:30.287+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.stream.branch;

import me.study.springcloud.stream.WordCount;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
@EnableBinding({ProcessorBinding.class, KafkaStreamsProcessor.class})
public class BranchDemoBean {


    @StreamListener(ProcessorBinding.BRANCH_INPUT)
    @SendTo({ProcessorBinding.BRANCH_OUTPUT_1, ProcessorBinding.BRANCH_OUTPUT_2, ProcessorBinding.BRANCH_OUTPUT_3})
    @SuppressWarnings({"unchecked", "SpringCloudStreamInconsistencyInspection"})
    public KStream<String, String>[] branch(KStream<String, String> input) {

        Predicate<String, String> output1 = (k, v) -> k.equals("1");
        Predicate<String, String> output2 = (k, v) -> k.equals("2");
        Predicate<String, String> output3 = (k, v) -> k.equals("3");

        return input
                .branch(output1, output2, output3);
    }


    private static final String INPUT_TOPIC = "input";
    private static final String OUTPUT_TOPIC = "output";
    private static final int WINDOW_SIZE_MS = 1000;


    @StreamListener(INPUT_TOPIC)
    @SendTo(OUTPUT_TOPIC)
    public KStream<Bytes, WordCount> process(KStream<String, String> input) {

        return input
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                .map((key, value) -> new KeyValue<>(value, value))
                .groupByKey(Serialized.with(Serdes.String(), Serdes.String()))
                .windowedBy(TimeWindows.of(WINDOW_SIZE_MS))
                .count(Materialized.as("WordCounts-1"))
                .toStream()
                .map((key, value) -> new KeyValue<>(null,
                                                    new WordCount(key.key(),
                                                                  value,
                                                                  new Date(key.window().start()),
                                                                  new Date(key.window().end()))));
    }
}
