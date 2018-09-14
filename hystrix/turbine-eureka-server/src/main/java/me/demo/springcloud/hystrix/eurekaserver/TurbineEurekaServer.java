package me.demo.springcloud.hystrix.eurekaserver;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("me.demo.springcloud.hystrix.eurekaserver")
@SpringBootApplication
@EnableEurekaServer
@EnableTurbineStream
public class TurbineEurekaServer {
}
