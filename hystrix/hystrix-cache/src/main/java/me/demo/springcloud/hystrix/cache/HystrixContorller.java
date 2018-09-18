package me.demo.springcloud.hystrix.cache;


import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HystrixContorller {

    @Autowired
    private CacheServices okServices;

    @RequestMapping("/cache1")
    public String toRead(@RequestParam("id") String id) {
        try (HystrixRequestContext context = HystrixRequestContext.initializeContext()) {
            return String.valueOf(okServices.tryCache(id).getRandomNumber());
        }
    }
}
