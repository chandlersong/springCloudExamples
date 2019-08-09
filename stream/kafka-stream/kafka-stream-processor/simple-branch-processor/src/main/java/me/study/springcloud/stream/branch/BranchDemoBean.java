/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-09T22:14:50.213+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.stream.branch;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding({ProcessorBinding.class})
public class BranchDemoBean {


    @StreamListener(ProcessorBinding.BRANCH_INPUT)
    @SendTo({ProcessorBinding.BRANCH_OUTPUT_1, ProcessorBinding.BRANCH_OUTPUT_2, ProcessorBinding.BRANCH_OUTPUT_3})
    @SuppressWarnings("unchecked")
    public KStream<String, String>[] branch(KStream<String, String> input) {

        Predicate<String, String> output1 = (k, v) -> k.equals("1");
        Predicate<String, String> output2 = (k, v) -> k.equals("2");
        Predicate<String, String> output3 = (k, v) -> k.equals("3");

        return input
                .branch(output1, output2, output3);
    }


}
