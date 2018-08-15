package me.demo.springcloud.eureka.client;

import com.netflix.appinfo.ApplicationInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import java.util.Map;

@ComponentScan("me.demo.springcloud.eureka.client")
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaClientApplication implements CommandLineRunner {

    @Autowired
    private ApplicationInfoManager aim;

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Map<String, String> map = aim.getInfo().getMetadata();
        map.put("addByCodeMeta", "add_when_initial");
    }
}



