package me.demo.springcloud.eureka.client;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * it will be called each time /info called
 */
@Component
public class SampleInfoContributor implements InfoContributor {

    private  static int called = 0;
    @Override
    public void contribute(Info.Builder builder) {
        called ++;
        builder.withDetail("dynamic_value", called);
    }
}
