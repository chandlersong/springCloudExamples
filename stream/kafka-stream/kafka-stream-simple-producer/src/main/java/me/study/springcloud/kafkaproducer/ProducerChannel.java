/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-19T22:22:39.539+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafkaproducer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProducerChannel {

    String BRANCH_TEST = "BRANCH_TEST";

    String INTERACTIVE_QUERY_TEST = "INTERACTIVE_QUERY_TEST";

    @Output(BRANCH_TEST)
    MessageChannel branch();

    @Output(INTERACTIVE_QUERY_TEST)
    MessageChannel interactiveQuery();
}
