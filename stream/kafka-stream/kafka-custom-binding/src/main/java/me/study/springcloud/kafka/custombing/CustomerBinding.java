/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-07-27T16:17:40.281+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafka.custombing;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CustomerBinding {

    String INPUT = "myInput";
    String OUTPUT = "myOutput";

    @Output(OUTPUT)
    MessageChannel anOutput();

    @Input(INPUT)
    MessageChannel anInput();
}
