package me.demo.springcloud.hystrix.demo;

import me.demo.springcloud.hystrix.simple.BasicHystrixApplication;
import me.demo.springcloud.utils.RestTemplateWrapper;
import me.demo.springcloud.utils.ServerRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringJUnit4ClassRunner.class)
public class HystrixCollapseDemos {

    private static final Logger logger = getLogger(HystrixCollapseDemos.class);

    private RestTemplateWrapper template = new RestTemplateWrapper();

    /**
     * Cache is only at one request. if you will call down stream service multi-times, it will reduce some cost
     */
    @Test
    public void testBasicCollapse() {
        logger.info("server port:{}", "8080");
        ServerRunner.createAndRunServer(BasicHystrixApplication.class, "simplest_hystrix_server.yml");

        template.doGet("/collapse");

        logger.info("ck services down");
        logger.info("stop");
    }

}
