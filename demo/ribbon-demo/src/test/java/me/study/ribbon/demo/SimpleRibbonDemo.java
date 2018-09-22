package me.study.ribbon.demo;

import me.demo.springcloud.eureka.server.EurekaServerApplication;
import me.demo.springcloud.services.ok.OKServicesApplication;
import me.demo.springcloud.utils.ServerRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleRibbonDemo {

    private static final Logger logger = getLogger(SimpleRibbonDemo.class);

    @Test
    public void runHelloWorld() {

        ServerRunner.createAndRunServer(EurekaServerApplication.class);
        ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_1.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_2.yml");


        logger.info("stop");

    }
}
