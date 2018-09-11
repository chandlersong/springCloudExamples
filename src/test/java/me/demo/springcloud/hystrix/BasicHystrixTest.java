package me.demo.springcloud.hystrix;

import me.demo.springcloud.eureka.server.EurekaServerApplication;
import me.demo.springcloud.services.ok.OKServicesApplication;
import me.demo.springcloud.utils.ServerRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringJUnit4ClassRunner.class)
public class BasicHystrixTest {

    private static final Logger logger = getLogger(BasicHystrixTest.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testHelloWorld() {
        logger.info("server port:{}", "8080");
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "simplest_eureka_server.yml");
        ServerRunner andRunServer = ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_1.yml");
        ServerRunner.createAndRunServer(BasicHystrixApplication.class, "simplest_hystrix_server.yml");

        logger.info("return value:{} ", callHystrix());
        logger.info("ck services start");
        andRunServer.stop();
        logger.info("ck services down");
        logger.info("return value:{} ", callHystrix());
        logger.info("stop");
    }

    private String callHystrix() {
        return restTemplate.getForEntity("http://localhost:8080/mustSay", String.class).getBody();
    }
}
