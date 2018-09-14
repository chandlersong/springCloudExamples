package me.demo.springcloud.eureka.lifecycleclient;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class EurekaHeathListener implements HealthCheckHandler {

    private static final Logger logger = getLogger(EurekaHeathListener.class);

    private Optional<InstanceInfo.InstanceStatus> status = Optional.empty();


    public void setStatus(InstanceInfo.InstanceStatus status) {
        this.status = Optional.ofNullable(status);
    }


    @Override
    public InstanceInfo.InstanceStatus getStatus(InstanceInfo.InstanceStatus currentStatus) {
        InstanceInfo.InstanceStatus instanceStatus = status.orElse(currentStatus);
        logger.info("current instance status to {}", instanceStatus.name());
        return instanceStatus;
    }

}
