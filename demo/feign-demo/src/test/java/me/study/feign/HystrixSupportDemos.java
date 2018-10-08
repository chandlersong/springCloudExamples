/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-08T22:12:54.950+08:00
 * LGPL licence
 *
 */

package me.study.feign;

import me.demo.springcloud.utils.RestRequest;
import me.demo.springcloud.utils.ServerRunner;
import me.study.hystrixfeign.HystrixFeignApplication;
import me.study.springcloud.services.ok.OKServicesApplication;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class HystrixSupportDemos {

    private static final Logger logger = getLogger(HystrixSupportDemos.class);

    @Test
    public void testReturnCommand() {
        logger.info("server port:{}", "8080");
        startTestServer();

        Assert.assertEquals("client1", RestRequest.get("/hystrix_say"));
        logger.info("stop");
    }

    @Test
    public void testReturnObservable() {
        logger.info("server port:{}", "8080");
        startTestServer();


        Assert.assertEquals("client1", RestRequest.get("/hystrix_observable"));
        logger.info("stop");
    }

    @Test
    public void testReturnFallBack() {
        logger.info("server port:{}", "8080");

        ServerRunner.createAndRunServer(HystrixFeignApplication.class, "hystrix_feign_server.yml");
        Assert.assertEquals("fallback", RestRequest.get("/hystrix_observable"));
        Assert.assertEquals("fallback", RestRequest.get("/hystrix_say"));
        logger.info("stop");
    }

    private void startTestServer() {
        ServerRunner.createAndRunServer(HystrixFeignApplication.class, "hystrix_feign_server.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_1_without_eureka.yml");
    }
}
