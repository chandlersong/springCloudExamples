/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-09T00:13:37.497+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafkaproducer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProducerChannel {

    String BRANCH_TEST = "BRANCH_TEST";


    @Output(BRANCH_TEST)
    MessageChannel branch();
}
