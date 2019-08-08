/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-09T00:13:37.489+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafkacomsumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface ListenerBinding {

    String BRANCH_1 = "branch_demo_1";
    String BRANCH_2 = "branch_demo_2";
    String BRANCH_3 = "branch_demo_3";


    @Input(BRANCH_1)
    MessageChannel branch1();

    @Input(BRANCH_2)
    MessageChannel branch2();

    @Input(BRANCH_3)
    MessageChannel branch3();
}
