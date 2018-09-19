package me.demo.springcloud.hystrix.simple;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CacheServices {

    private Random r = new Random();

    @CacheResult
    @HystrixCommand(commandKey = "cacheService", fallbackMethod = "fallBackMethod")
    public User tryCache(@CacheKey String id) {

        if (r.nextInt(10) > 5) {
            throw new RuntimeException();
        }
        return new User(id);
    }

    public User fallBackMethod(String id) {
        User user = new User(id);
        user.setRandomNumber(-1);
        return user;
    }

}
