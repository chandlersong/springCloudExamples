/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-09T22:14:50.225+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafka.stream.wordcount;

import me.study.springcloud.stream.WordCount;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Serialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
@EnableBinding({KafkaStreamsProcessor.class})
public class WordCountApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordCountApplication.class);
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
