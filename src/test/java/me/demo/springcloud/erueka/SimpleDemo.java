package me.demo.springcloud.erueka;

import me.demo.springcloud.erueka.server.EurekaServerApplication;
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
    public void runSimpleDemo() {
        logger.info("simplest demo of eureka,only on eurkea server");
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "simplest_eureka_server.yml");
        logger.info("you can add breakpoint here and check server page");

    }
}
