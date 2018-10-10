/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-11T06:52:46.888+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.cofig.simpleclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SimpleConfigClient {

    private String value;

    @RequestMapping("/")
    public String home() {
        return value;
    }

    public String getValue() {
        return value;
    }

    @Value("${test-value:nonLoad}")
    public void setValue(String value) {
        this.value = value;
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleConfigClient.class, args);
    }
}
