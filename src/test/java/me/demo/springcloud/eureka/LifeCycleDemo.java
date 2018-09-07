package me.demo.springcloud.eureka;

import me.demo.springcloud.eureka.lifecycleclient.EurekaLifeCycleClientApplication;
import me.demo.springcloud.eureka.server.EurekaServerApplication;
import me.demo.springcloud.utils.ServerRunner;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class LifeCycleDemo {

    private Logger logger = LoggerFactory.getLogger(SimpleDemo.class);

    @Test
    public void upAndDown() throws InterruptedException {
        logger.info("simplest demo of eureka,only on eurkea server");
        logger.info("server port:{}, client port:{}", "8761", "8762");
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "simplest_eureka_server.yml");
        ServerRunner.createAndRunServer(EurekaLifeCycleClientApplication.class, "lifecycle_eureka_client.yml");

        //make sure dynamicMeta will work
        Thread.sleep(60 * 1000);


        logger.info("you can add breakpoint here and check server page");
    }

}
