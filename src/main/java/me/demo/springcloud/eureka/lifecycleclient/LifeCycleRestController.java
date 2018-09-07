package me.demo.springcloud.eureka.lifecycleclient;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public @RestController class LifeCycleRestController {

    private Logger logger = LoggerFactory.getLogger(LifeCycleRestController.class);


    @Autowired
    private ApplicationInfoManager aim;

    @Autowired
    private EurekaHeathListener healthCheckHandler;

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

    @RequestMapping(value = "/upByHandler", produces = "application/json")
    public String upByHandler() {
        healthCheckHandler.setStatus(InstanceInfo.InstanceStatus.UP);
        logger.info("up was called!!!");
        return "up";
    }

    @RequestMapping(value = "/downByHandler", produces = "application/json")
    public String downByHandler() {
        healthCheckHandler.setStatus(InstanceInfo.InstanceStatus.DOWN);
        logger.info("down was called!!!");
        return "down";
    }

    @RequestMapping(value = "/outofserviceByHandle", produces = "application/json")
    public String outOfServiceByHandler() {
        healthCheckHandler.setStatus(InstanceInfo.InstanceStatus.OUT_OF_SERVICE);
        logger.info("out of service was called!!!");
        return "out of Service";
    }
}
