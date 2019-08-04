/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-04T16:56:03.753+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafkacomsumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface JsonBinding {

    String JSON_CONSUMER = "JSON_CONSUMER";

    @Input(JSON_CONSUMER)
    MessageChannel jsonConsumer();
}
