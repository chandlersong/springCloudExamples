package me.study.springcloud.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

@RestController
@SpringBootApplication
public class SimpleKafkaApplication {

    private ConcurrentLinkedQueue<Person> names = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        SpringApplication.run(SimpleKafkaApplication.class, args);
    }


    @GetMapping("/hello")
    public void hello(@RequestParam String name){
        Person p = new Person();
        p.setName(name);
        names.add(p);
    }

    @Bean
    public Supplier<Person> sayHi() {
        return () -> names.poll();
    }

    @Bean
    public Consumer<Person> log() {
        return person -> System.out.println("Received: " + person);
    }

    public static class Person {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String toString() {
            return this.name;
        }
    }
}
