package me.demo.springcloud.eureka.lifecycleclient;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
/**
 * this application is try to work of the lifecycle
 */

@ComponentScan("me.demo.springcloud.eureka.lifecycleclient")
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaLifeCycleClientApplication implements CommandLineRunner {


    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private EurekaHeathListener healthCheckHandler;


    public static void main(String[] args) {
        SpringApplication.run(EurekaLifeCycleClientApplication.class, args);
    }

    @Override
    public void run(String... args){
        eurekaClient.registerHealthCheck(healthCheckHandler);
    }
}



