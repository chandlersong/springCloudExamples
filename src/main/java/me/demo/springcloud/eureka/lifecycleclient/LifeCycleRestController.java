package me.demo.springcloud.eureka.lifecycleclient;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

public @RestController class LifeCycleRestController {

    private Logger logger = LoggerFactory.getLogger(LifeCycleRestController.class);
    @Autowired
    private DiscoveryClient discoveryClient;


    @Autowired
    private ApplicationInfoManager aim;

    @RequestMapping(value = "/up", produces = "application/json")
    public String up() {
        aim.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
        logger.info("up was called!!!");
        return "up";
    }

    @RequestMapping(value = "/down", produces = "application/json")
    public String down() {
        aim.setInstanceStatus(InstanceInfo.InstanceStatus.DOWN);
        logger.info("down was called!!!");
        return "down";
    }

    @RequestMapping(value = "/outofservice", produces = "application/json")
    public String outOfService() {
        aim.getInfo().setStatus(InstanceInfo.InstanceStatus.OUT_OF_SERVICE);
        logger.info("out of service was called!!!");
        return "down";
    }
}
