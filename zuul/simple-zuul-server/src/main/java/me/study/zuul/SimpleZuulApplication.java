package me.study.zuul;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableZuulProxy
@Configuration
public class SimpleZuulApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SimpleZuulApplication.class).run(args);
    }

    @Bean
    public PatternServiceRouteMapper serviceRouteMapper() {
        return new PatternServiceRouteMapper(
                "ok_(?<name>client\\d)",
                "${name}");
    }
}
