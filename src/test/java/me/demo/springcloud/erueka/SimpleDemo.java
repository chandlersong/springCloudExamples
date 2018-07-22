package me.demo.springcloud.erueka;

import me.demo.springcloud.erueka.server.EurekaServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleDemo {

    private Logger logger = LoggerFactory.getLogger(SimpleDemo.class);
    @Test
    public void runSimpleDemo(){
        logger.info("simplest demo of eureka,only on eurkea server");
        SpringApplicationBuilder eurekaServer = new SpringApplicationBuilder(EurekaServerApplication.class)
                .properties("spring.config.location=classpath:simplest_eureka_server.yml");
        eurekaServer.run();
        logger.info("you can add breakpoint here and check server page");

    }
}
