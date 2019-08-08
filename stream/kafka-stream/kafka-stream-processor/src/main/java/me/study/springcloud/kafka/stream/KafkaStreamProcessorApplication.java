/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-09T00:13:37.482+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafka.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaStreamProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamProcessorApplication.class, args);
    }
}
