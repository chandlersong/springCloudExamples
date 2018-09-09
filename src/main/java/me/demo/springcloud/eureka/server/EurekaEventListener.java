package me.demo.springcloud.eureka.server;

import org.slf4j.Logger;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;

import static org.slf4j.LoggerFactory.getLogger;


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
        logger.info("EurekaInstanceRegisteredEvent was called:id{},app name{},instance info:{}", event.getServerId(), event.getAppName(), event.getInstanceInfo());
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
