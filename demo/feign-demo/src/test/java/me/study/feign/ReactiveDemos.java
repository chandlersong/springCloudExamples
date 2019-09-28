/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-28T23:01:12.934+08:00
 * LGPL licence
 *
 */

package me.study.feign;

import lombok.extern.slf4j.Slf4j;
import me.demo.springcloud.utils.RestRequest;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.eureka.server.EurekaServerApplication;
import me.study.springcloud.services.ok.ReactiveOKServiceApplication;
import org.junit.Test;

@Slf4j
public class ReactiveDemos {

    @Test
    public void testHelloWorld() {
        log.info("server port:{}", "8080");
        /**
         * run the application manually， and add a break point at request line
         */
        //ServerRunner.createAndRunServer(ReactiveFeignApplication.class, "reactive/application.yml");
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "simplest_eureka_server.yml");
        ServerRunner.createAndRunServer(ReactiveOKServiceApplication.class, "reactive/reactive_ok_services_client.yml");


        String response = RestRequest.get("/greeting");
        log.info("request get:,{}", response);
        log.info("stop");
    }
}
