package me.study.springcloud.kafka;

import me.study.entry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

@RestController
@SpringBootApplication
public class SimpleKafkaApplication {

    private ConcurrentLinkedQueue<User> names = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        SpringApplication.run(SimpleKafkaApplication.class, args);
    }

    @Autowired
    private StreamBridge bridge;

    @GetMapping("/hello")
    public void hello(@RequestParam String name) {
        User.Builder builder = User.newBuilder();
        builder.setName(name);
        builder.setFavoriteColor("bbbb");
        builder.setFavoriteNumber(2);
        names.add(builder.build());
    }

    @GetMapping("/steamBridge")
    public void streamBridge(@RequestParam String name) {
        User.Builder builder = User.newBuilder();
        builder.setName(name);
        builder.setFavoriteColor("bbbb");
        builder.setFavoriteNumber(2);
        bridge.send("avroTopic", builder.build(), MimeType.valueOf("application/*+avro"));
    }

    @Bean
    public Supplier<User> log() {
        return () -> names.poll();
    }

}
