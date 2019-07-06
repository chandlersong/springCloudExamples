/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-07-06T21:13:49.018+08:00
 * LGPL licence
 *
 */

package me.study.zookeeper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperAutoServiceRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ZookeeperDiscoveryApplication {


    private DiscoveryClient discoveryClient;


    public static void main(String[] args) {
        SpringApplication.run(ZookeeperDiscoveryApplication.class);
    }

    @RequestMapping(value = "/service-instances/{applicationName}", produces = "application/json")
    public String serviceUrl(@PathVariable String applicationName) {
        List<ServiceInstance> list = discoveryClient.getInstances(applicationName);
        if (list != null && list.size() > 0) {
            return list.get(0).getUri().toString();
        }
        return null;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void init(ApplicationStartedEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        ZookeeperRegistration registration = context.getBean(ZookeeperRegistration.class);
        registration.setPort(8080);
        ZookeeperAutoServiceRegistration serviceRegistration = context.getBean(ZookeeperAutoServiceRegistration.class);
        serviceRegistration.start();
    }

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }
}
