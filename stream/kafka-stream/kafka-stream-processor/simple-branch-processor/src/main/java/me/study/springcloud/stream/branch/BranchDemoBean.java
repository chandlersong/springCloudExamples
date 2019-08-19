/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-19T22:22:39.544+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.stream.branch;

import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import me.study.springcloud.stream.WordCount;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import static me.study.springcloud.stream.branch.ProcessorBinding.INTERACTIVE_QUERY;

@Controller
@Log4j2
@EnableBinding({ProcessorBinding.class, KafkaStreamsProcessor.class})
public class BranchDemoBean {

    private InteractiveQueryService queryService;

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


    @StreamListener(INTERACTIVE_QUERY)
    public void interactiveQuery(KStream<String, String> input) {
        input.map((key, value) -> new KeyValue<>(value, value))
                .groupByKey(Serialized.with(Serdes.String(), Serdes.String()))
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("interactiveQuery1")
                               .withKeySerde(Serdes.String())
                               .withValueSerde(Serdes.Long()));
    }


    @RequestMapping(value = "/InteractiveQuery", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Integer>> InteractiveQuery() {
        ReadOnlyKeyValueStore<Object, Object> store = queryService.getQueryableStore("interactiveQuery1",
                                                                                     QueryableStoreTypes.keyValueStore());

        KeyValueIterator<Object, Object> all = store.all();

        Map<String, Integer> result = Maps.newHashMap();
        while (all.hasNext()) {
            KeyValue<Object, Object> next = all.next();
            Object key = next.key;
            Object value = next.value;
            //key is String, value is long
            log.info("key is {},value is {}", key, value);
            result.put(key.toString(), Integer.valueOf(value.toString()));
        }

        return ResponseEntity.ok(result);
    }

    @Autowired
    public void setQueryService(InteractiveQueryService queryService) {
        this.queryService = queryService;
    }
}
