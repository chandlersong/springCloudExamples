/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-08T18:39:01.901+08:00
 * LGPL licence
 *
 */

package me.study.feign;

import me.demo.springcloud.utils.RestRequest;
import me.demo.springcloud.utils.ServerRunner;
import me.study.hystrixfeign.HystrixFeignApplication;
import me.study.simplefeign.SimpleFeignApplication;
import me.study.springcloud.eureka.server.EurekaServerApplication;
import me.study.springcloud.services.ok.OKServicesApplication;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class HystrixSupportDemos {

    private static final Logger logger = getLogger(HystrixSupportDemos.class);

    @Test
    public void testReturnFuture() throws InterruptedException {
        logger.info("server port:{}", "8080");
        ServerRunner.createAndRunServer(EurekaServerApplication.class);
        ServerRunner.createAndRunServer(HystrixFeignApplication.class, "hystrix_feign_server.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_1.yml");



        Thread.sleep(35 * 1000);
        Assert.assertEquals("client1", RestRequest.get("/hystrix_say"));
        logger.info("stop");
    }
}
