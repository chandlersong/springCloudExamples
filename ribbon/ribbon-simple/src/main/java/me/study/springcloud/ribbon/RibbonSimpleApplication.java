package me.study.springcloud.ribbon;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@RibbonClient(name = "okservice", configuration = SayOKConfiguration.class)
public class RibbonSimpleApplication {


    @Autowired
    RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RibbonSimpleApplication.class, args);
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping("/hi")
    public String ok() {
        return this.restTemplate.getForObject("http://ok-service/say", String.class);
    }
}
