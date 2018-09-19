package me.demo.springcloud.hystrix.simple;


import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

import java.util.concurrent.Future;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class HystrixController {

    private static final Logger logger = getLogger(HystrixController.class);

    @Autowired
    private OKServices okServices;

    @Autowired
    private CacheServices cacheService;

    @Autowired
    private CollapseService collapseService;

    @RequestMapping("/mustSay")
    public String toRead() {
        return okServices.sayOk();
    }


    @RequestMapping("/collapse")
    public void collapse() {

        try (HystrixRequestContext context = HystrixRequestContext.initializeContext()) {

            Future<User> user1 = collapseService.getUserByIdAsync("user1");
            Observable<User> user2 = collapseService.getUserByIdReact("user2");
            Observable<User> user3 = collapseService.getUserByIdReact("user3");

            Iterable<User> users = Observable.merge(user2, user3).toBlocking().toIterable();

            for (User cUser : users) {
                logger.info("user,{}", cUser.getId());
            }
        }
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
