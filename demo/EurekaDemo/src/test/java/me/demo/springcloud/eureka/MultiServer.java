package me.demo.springcloud.eureka;

import me.demo.springcloud.eureka.client.EurekaClientApplication;
import me.demo.springcloud.eureka.server.EurekaServerApplication;
import me.demo.springcloud.utils.ServerRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * please
 * 127.0.0.1   zone1
 * 127.0.0.1   zone2
 * 127.0.0.1   zone3
 * to you host file
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class MultiServer {

    private static final Logger logger = getLogger(MultiServer.class);

    @Test
    public void runALLInOne() throws InterruptedException {
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "multi_eureka_server_1.yml");
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "multi_eureka_server_2.yml");

        Thread.sleep(30 * 1000);
        ServerRunner.createAndRunServer(EurekaClientApplication.class, "multi_eureka_server_1_client1.yml");
        ServerRunner.createAndRunServer(EurekaClientApplication.class, "multi_eureka_server_2_client1.yml");
        logger.info("stop");

    }

    @Test
    public void runAllClient() {
        ServerRunner.createAndRunServer(EurekaClientApplication.class, "multi_eureka_server_1_client1.yml");
        ServerRunner.createAndRunServer(EurekaClientApplication.class, "multi_eureka_server_2_client1.yml");
        ServerRunner.createAndRunServer(EurekaClientApplication.class, "multi_eureka_server_3_client1.yml");

        logger.info("stop");
    }

    @Test
    public void runThreeServer() {
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "multi_eureka_server_1.yml");
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "multi_eureka_server_2.yml");
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "multi_eureka_server_3.yml");


        logger.info("stop");
    }


    @Test
    public void runServer1() {
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "multi_eureka_server_1.yml");

        logger.info("stop");

    }


    @Test
    public void runServer2() {
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "multi_eureka_server_2.yml");

        logger.info("stop");

    }


    @Test
    public void runServer3() {
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "multi_eureka_server_3.yml");

        logger.info("stop");

    }

}
