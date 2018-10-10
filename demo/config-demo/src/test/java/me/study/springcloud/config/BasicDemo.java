/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-11T00:00:57.623+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.config;

import me.demo.springcloud.utils.RestRequest;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.cofig.simpleclient.SimpleConfigClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringJUnit4ClassRunner.class)
public class BasicDemo {

    private static final Logger logger = getLogger(BasicDemo.class);

    @Test
    public void testHelloWorld() {

        ServerRunner.createAndRunServer(SimpleConfigServer.class, "simple_config_server_without_eureka.yml");
        ServerRunner.createAndRunServer(SimpleConfigClient.class, "simple_config_client_without_eureka.yml", "simple_config_client_bootstrap.yml");

        Assert.assertEquals("spam", RestRequest.get("localhost", 8081, ""));
        logger.info("stop");
    }
}
