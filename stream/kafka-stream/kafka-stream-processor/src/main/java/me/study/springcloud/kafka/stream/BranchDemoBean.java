/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-09T00:13:37.478+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafka.stream;

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
    @SuppressWarnings("unchecked")
    public KStream<Object, WordCount>[] branch(KStream<String, String> input) {

        Predicate<Object, WordCount> output1 = (k, v) -> String.valueOf(v.getWord().length() % 3).equals("0");
        Predicate<Object, WordCount> output2 = (k, v) -> String.valueOf(v.getWord().length() % 3).equals("1");
        Predicate<Object, WordCount> output3 = (k, v) -> String.valueOf(v.getWord().length() % 3).equals("2");

        return input
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                .groupBy((key, value) -> value)
                .windowedBy(TimeWindows.of(WINDOW_SIZE_MS))
                .count(Materialized.as("Branch-1"))
                .toStream()
                .map((key, value) -> new KeyValue<>(null,
                                                    new WordCount(key.key(),
                                                                  value,
                                                                  new Date(key.window().start()),
                                                                  new Date(key.window().end()))))
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
