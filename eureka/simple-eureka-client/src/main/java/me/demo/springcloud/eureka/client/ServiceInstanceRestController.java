package me.demo.springcloud.eureka.client;

import com.netflix.appinfo.ApplicationInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

public @RestController
class ServiceInstanceRestController {

    private static int invoked = 0;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private ApplicationInfoManager aim;

    @RequestMapping(value = "/service-instances/{applicationName}", produces = "application/json")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {

        Map<String, String> map = aim.getInfo().getMetadata();
        map.put("dynamicMeta", String.valueOf(invoked));
        invoked++;
        aim.registerAppMetadata(map);

        return this.discoveryClient.getInstances(applicationName);
    }
}
