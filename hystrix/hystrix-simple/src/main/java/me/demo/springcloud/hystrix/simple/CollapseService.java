package me.demo.springcloud.hystrix.simple;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class CollapseService {

    private static final Logger logger = getLogger(CollapseService.class);

    @HystrixCollapser(batchMethod = "getUserByIds")
    public Future<User> getUserByIdAsync(String id) {
        return null;
    }

    /**
     * Reactive Execution
     */
    @HystrixCollapser(batchMethod = "getUserByIds")
    public Observable<User> getUserByIdReact(String id) {
        return null;
    }

    @HystrixCommand
    public List<User> getUserByIds(List<String> ids) {
        logger.info("collapse method call!!!");
        List<User> users = new ArrayList<User>();
        for (String id : ids) {
            users.add(new User(id));
        }
        return users;
    }
}
