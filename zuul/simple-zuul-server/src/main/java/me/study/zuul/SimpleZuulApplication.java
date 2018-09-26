package me.study.zuul;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class SimpleZuulApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SimpleZuulApplication.class).run(args);
    }
}
