package me.study.feign;

import me.demo.springcloud.utils.RestRequest;
import me.demo.springcloud.utils.ServerRunner;
import me.study.simplefeign.SimpleFeignApplication;
import me.study.springcloud.eureka.server.EurekaServerApplication;
import me.study.springcloud.services.ok.OKServicesApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringJUnit4ClassRunner.class)
public class BasicFeignDemos {

    private static final Logger logger = getLogger(BasicFeignDemos.class);

    @Test
    public void testHelloWorld() throws InterruptedException {
        logger.info("server port:{}", "8080");
        ServerRunner.createAndRunServer(EurekaServerApplication.class);
        ServerRunner.createAndRunServer(SimpleFeignApplication.class, "simple_feign_server.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_1.yml");

        Thread.sleep(35 * 1000);
        Assert.assertEquals("client1",RestRequest.get("/sayHi"));
        logger.info("stop");
    }
}
