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
public class HystrixCacheDemos {

    private static final Logger logger = getLogger(HystrixCacheDemos.class);

    private RestTemplateWrapper template = new RestTemplateWrapper();

    /**
     * Cache is only at one request. if you will call down stream service multi-times, it will reduce some cost
     */
    @Test
    public void testBasicCache() {
        logger.info("server port:{}", "8080");
        ServerRunner.createAndRunServer(BasicHystrixApplication.class, "simplest_hystrix_server.yml");

        for (int i = 0; i < 10; i++) {
            logger.info("call web services {} times", i);
            template.doGet("/cache1?id=abc");
        }
        logger.info("ck services down");
        logger.info("stop");
    }

}
