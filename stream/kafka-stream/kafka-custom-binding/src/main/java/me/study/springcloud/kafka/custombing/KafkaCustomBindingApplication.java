/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-07-27T16:17:40.285+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafka.custombing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@Slf4j
@EnableBinding(CustomerBinding.class)
@SpringBootApplication
public class KafkaCustomBindingApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaCustomBindingApplication.class);
    }


}
