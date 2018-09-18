package me.demo.springcloud.hystrix.demo;

import me.demo.springcloud.hystrix.cache.HystrixCacheApplication;
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

    @Test
    public void testBasicCache() {
        logger.info("server port:{}", "8080");
        ServerRunner.createAndRunServer(HystrixCacheApplication.class, "simplest_hystrix_server.yml");

        for (int i = 0; i < 20; i++) {
            logger.info("return result:{}!!!", template.doGet("/cache1?id=abc"));
        }
        logger.info("ck services down");
        logger.info("stop");
    }

}
