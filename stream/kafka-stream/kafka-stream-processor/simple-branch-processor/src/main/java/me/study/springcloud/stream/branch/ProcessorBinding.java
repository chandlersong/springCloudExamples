/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-19T22:22:39.550+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.stream.branch;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface ProcessorBinding {

    String BRANCH_INPUT = "Branch_DEMO";

    String BRANCH_OUTPUT_1 = "Branch_output_1";

    String BRANCH_OUTPUT_2 = "Branch_output_2";

    String BRANCH_OUTPUT_3 = "Branch_output_3";

    String INTERACTIVE_QUERY = "INTERACTIVE_QUERY";

    @Input(BRANCH_INPUT)
    KStream<String, String> branchInput();

    @Input(INTERACTIVE_QUERY)
    KStream<String, String> interactiveQuery();

    @Output(BRANCH_OUTPUT_1)
    KStream<String, String> branchOutput1();

    @Output(BRANCH_OUTPUT_2)
    KStream<String, String> branchOutput2();

    @Output(BRANCH_OUTPUT_3)
    KStream<String, String> branchOutput3();
}
