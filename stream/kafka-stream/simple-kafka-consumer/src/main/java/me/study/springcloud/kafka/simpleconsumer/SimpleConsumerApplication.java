package me.study.springcloud.kafka.simpleconsumer;

import lombok.extern.slf4j.Slf4j;
import me.study.entry.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;


@Slf4j
@SpringBootApplication
public class SimpleConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleConsumerApplication.class, args);
    }


    @Bean
    public Consumer<User> simpleAvro() {
        return u -> log.info("receive user,{}", u);
    }

}
