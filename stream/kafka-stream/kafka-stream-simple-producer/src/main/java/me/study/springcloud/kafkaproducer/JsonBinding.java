/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-04T16:56:03.762+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafkaproducer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface JsonBinding {

    String JSON_PRODUCER = "jsonProducer";

    @Output(JSON_PRODUCER)
    MessageChannel sendChannel();
}
