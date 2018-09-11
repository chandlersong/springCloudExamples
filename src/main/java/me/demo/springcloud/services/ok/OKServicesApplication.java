package me.demo.springcloud.services.ok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("me.demo.springcloud.services.ok")
@SpringBootApplication
@EnableDiscoveryClient
public class OKServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(OKServicesApplication.class, args);
    }
}
