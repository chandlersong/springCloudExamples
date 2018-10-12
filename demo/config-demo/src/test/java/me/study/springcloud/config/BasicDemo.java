/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-12T21:24:04.632+08:00
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
        ServerRunner.createAndRunServer(SimpleConfigClient.class, "simple_config_client_without_eureka.yml", "simple_config_client_bootstrap-default.yml");
        ServerRunner.createAndRunServer(SimpleConfigClient.class, "simple_config_client_without_eureka.yml", "simple_config_client_bootstrap-dev1.yml");
        ServerRunner.createAndRunServer(SimpleConfigClient.class, "simple_config_client_without_eureka.yml", "simple_config_client_bootstrap-dev2.yml");

        Assert.assertEquals("default", RestRequest.get(8081, ""));
        Assert.assertEquals("dev", RestRequest.get(8082, ""));
        Assert.assertEquals("dev", RestRequest.get(8083, ""));
        logger.info("stop");
    }


    @Test
    public void testLocalRepo() {

        ServerRunner.createAndRunServer(SimpleConfigServer.class, "local_config_server_without_eureka.yml");
        ServerRunner.createAndRunServer(SimpleConfigClient.class, "simple_config_client_without_eureka.yml", "simple_config_client_bootstrap-default.yml");
        ServerRunner.createAndRunServer(SimpleConfigClient.class, "simple_config_client_without_eureka.yml", "simple_config_client_bootstrap-dev1.yml");
        ServerRunner.createAndRunServer(SimpleConfigClient.class, "simple_config_client_without_eureka.yml", "simple_config_client_bootstrap-dev2.yml");

        Assert.assertEquals("default", RestRequest.get(8081, ""));
        Assert.assertEquals("dev", RestRequest.get(8082, ""));
        Assert.assertEquals("dev", RestRequest.get(8083, ""));
        logger.info("stop");
    }



}
