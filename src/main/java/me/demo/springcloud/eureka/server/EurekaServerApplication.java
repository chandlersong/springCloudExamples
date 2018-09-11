package me.demo.springcloud.eureka.server;

import com.netflix.discovery.EurekaClient;
import me.demo.springcloud.eureka.lifecycleclient.EurekaHeathListener;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

import static org.slf4j.LoggerFactory.getLogger;

@ComponentScan("me.demo.springcloud.eureka.server")
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication implements CommandLineRunner {

    private static final Logger logger = getLogger(EurekaServerApplication.class);

    @Autowired
    private EurekaClient eurekaClient;

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        eurekaClient.registerHealthCheck(new EurekaHeathListener());
    }
}
