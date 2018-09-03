package me.demo.springcloud.eureka.lifecycleclient;

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
public class EurekaLifeCycleClientApplication {


    public static void main(String[] args) {
        SpringApplication.run(EurekaLifeCycleClientApplication.class, args);
    }
}



