package me.demo.springcloud.hystrix.simple;


import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class HystrixController {

    private static final Logger logger = getLogger(HystrixController.class);
    @Autowired
    private OKServices okServices;

    @Autowired
    private CacheServices cacheService;

    @RequestMapping("/mustSay")
    public String toRead() {
        return okServices.sayOk();
    }


    @RequestMapping("/cache1")
    public String toRead(@RequestParam("id") String id) {
        try (HystrixRequestContext context = HystrixRequestContext.initializeContext()) {

            for (int i = 0; i < 10; i++) {
                logger.info("{} value form cache services", cacheService.tryCache(id).getRandomNumber());
            }

            return id;
        }
    }
}
