package me.demo.springcloud.hystrix.demo;


import me.demo.springcloud.hystrix.simple.BasicHystrixApplication;
import me.demo.springcloud.services.ok.OKServicesApplication;
import me.demo.springcloud.utils.RestTemplateWrapper;
import me.demo.springcloud.utils.ServerRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringJUnit4ClassRunner.class)
public class BasicHystrixTest {

    private static final Logger logger = getLogger(BasicHystrixTest.class);


    private RestTemplateWrapper template = new RestTemplateWrapper();
    @Test
    public void testHelloWorld() {
        logger.info("server port:{}", "8080");
        ServerRunner andRunServer = ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_1.yml");
        ServerRunner.createAndRunServer(BasicHystrixApplication.class, "simplest_hystrix_server.yml");

        logger.info("return value:{} ", template.doGet("mustSay"));
        logger.info("ck services start");
        andRunServer.stop();
        logger.info("ck services down");
        logger.info("return value:{} ", template.doGet("mustSay"));
        logger.info("stop");
    }


    @Test
    public void testDashBoard() {
        logger.info("server port:{}", "8080");
        ServerRunner andRunServer = ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_1.yml");
        ServerRunner.createAndRunServer(BasicHystrixApplication.class, "simplest_hystrix_server.yml");


        logger.info("stop");
        for (int i = 0; i < 100; i++) {

            template.doGet("mustSay");

            if (i > 90) {
                andRunServer.stop();
            }
        }
        logger.info("stop");
    }


}
