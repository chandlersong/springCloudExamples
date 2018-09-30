package me.study.springcloud.services.ok;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class OkController {

    private static final Logger logger = getLogger(OkController.class);

    @Value("${ok-service-meta.clientId:hello}")
    private String clientId;

    @RequestMapping(value = "/say")
    public String sayOk() {
        return clientId;
    }

}
