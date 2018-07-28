package me.demo.springcloud.eureka;

import me.demo.springcloud.eureka.client.EurekaClientApplication;
import me.demo.springcloud.eureka.server.EurekaServerApplication;
import me.demo.springcloud.utils.ServerRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleDemo {

    private Logger logger = LoggerFactory.getLogger(SimpleDemo.class);

    @Test
    public void runSimpleServer() {
        logger.info("simplest demo of eureka,only on eurkea server");
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "simplest_eureka_server.yml");
        ServerRunner.createAndRunServer(EurekaClientApplication.class, "simplest_eureka_client.yml");
        logger.info("you can add breakpoint here and check server page");
    }
}
