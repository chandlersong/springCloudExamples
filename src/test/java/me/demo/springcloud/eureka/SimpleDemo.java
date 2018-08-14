package me.demo.springcloud.eureka;

import me.demo.springcloud.eureka.client.EurekaClientApplication;
import me.demo.springcloud.eureka.server.EurekaServerApplication;
import me.demo.springcloud.utils.ServerRunner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleDemo {

    private Logger logger = LoggerFactory.getLogger(SimpleDemo.class);

    private RestTemplate template;

    @Test
    public void runSimpleServer() throws InterruptedException, JSONException {
        logger.info("simplest demo of eureka,only on eurkea server");
        logger.info("server port:{}, client port:{}", "8761", "8762");
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "simplest_eureka_server.yml");
        ServerRunner.createAndRunServer(EurekaClientApplication.class, "simplest_eureka_client.yml");

        Thread.sleep(30 * 1000);

        Map<String, String> map = new HashMap<>();
        map.put("applicationName", "simplest-eureka-client");
        ResponseEntity<String> forEntity = template.getForEntity(
                "http://localhost:8762/service-instances/simplest-eureka-client", String.class);

        JSONArray jsonarray = new JSONArray(forEntity.getBody());
        logger.info("you can add breakpoint here and check server page");

    }

}
