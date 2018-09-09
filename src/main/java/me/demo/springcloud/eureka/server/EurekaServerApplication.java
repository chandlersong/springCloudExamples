package me.demo.springcloud.eureka.server;

import com.netflix.discovery.EurekaClient;
import me.demo.springcloud.eureka.lifecycleclient.EurekaHeathListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("me.demo.springcloud.eureka.server")
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication implements CommandLineRunner {

    @Autowired
    private EurekaClient eurekaClient;

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        eurekaClient.registerHealthCheck(new EurekaHeathListener());
    }
}
