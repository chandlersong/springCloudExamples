package me.study.springcloud.kafka;

import me.study.entry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class MessageExampleConfiguration {

    @Autowired
    private ProducerCommands producerCommands;

    @Bean
    public Supplier<User> log() {
        return () -> producerCommands.getNames().poll();
    }
}
