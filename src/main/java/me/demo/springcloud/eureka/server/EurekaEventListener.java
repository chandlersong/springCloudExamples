package me.demo.springcloud.eureka.server;

import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class EurekaEventListener {

    private static final Logger logger = getLogger(EurekaEventListener.class);

    @EventListener

    public void listen(EurekaInstanceCanceledEvent event) {
        logger.info("EurekaInstanceCanceledEvent was called:id:{},appName{}", event.getServerId(), event.getAppName());
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        logger.info("EurekaInstanceRegisteredEvent was called:instance info:{},Lease Duration {}", event.getInstanceInfo(), event.getLeaseDuration());
    }

    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        System.err.println(event.getServerId() + "\t" + event.getAppName() + " 服务进行续约");
        logger.info("EurekaInstanceRegisteredEvent was called:id{},app name{},instance info:{}", event.getServerId(), event.getAppName(), event.getInstanceInfo().getStatus());
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        logger.info("EurekaRegistryAvailableEvent,source {}", event.getSource());
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        logger.info("EurekaRegistryAvailableEvent,source {}", event.getSource());
    }

}
