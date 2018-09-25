package me.study.springcloud.eureka;

import me.demo.springcloud.eureka.client.EurekaClientApplication;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.eureka.server.EurekaServerApplication;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
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

    private RestTemplate template = new RestTemplate();

    /**
     * this UT show three way to add Eureka's meta data
     * 1 by configuration
     * check simplest_eureka_client.yml
     * 2. by code
     * check EurekaClientApplication's method run
     * 3 update meta data
     * check ServiceInstanceRestController-serviceInstancesByApplicationName
     * <p>
     * the client response please check resources/examples/simple_eureka_client.json
     */
    @Test
    public void aboutMetaData() throws InterruptedException, JSONException {
        logger.info("simplest demo of eureka,only on eurkea server");
        logger.info("server port:{}, client port:{}", "8761", "8762");
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "simplest_eureka_server.yml");
        ServerRunner.createAndRunServer(EurekaClientApplication.class, "simplest_eureka_client.yml");

        //make sure dynamicMeta will work
        Thread.sleep(60 * 1000);

        Map<String, String> map = new HashMap<>();
        map.put("applicationName", "simplest-eureka-client");
        ResponseEntity<String> forEntity = template.getForEntity(
                "http://localhost:8762/service-instances/simplest-eureka-client", String.class);

        JSONObject clientInfo = new JSONArray(forEntity.getBody()).getJSONObject(0);

        Assert.assertEquals("customizedValue",
                clientInfo.getJSONObject("metadata").getString("mykey"));
        Assert.assertEquals("add_when_initial",
                clientInfo.getJSONObject("metadata").getString("addByCodeMeta"));

        //    the field won't display immediately. please when client updata meta dta , it need to push to server.

        //Assert.assertNotNull(clientInfo.getJSONObject("metadata").has("dynamicMeta"));

        logger.info("you can add breakpoint here and check server page");
    }

    /**
     * the Info is use <br>InfoContributor</br> to change info by code
     * and static is use follow in
     * info:
     * app:
     * name: simplest-eureka-client
     * description: this is a very simple eureka client
     * version: 1.0.0
     * java-vendor: ${java.specification.vendor}
     * in configuration file
     */
    @Test
    public void aboutInfoPage() throws InterruptedException {
        logger.info("simplest demo of eureka,only on eurkea server");
        logger.info("server port:{}, client port:{}", "8761", "8762");
        ServerRunner.createAndRunServer(EurekaServerApplication.class, "simplest_eureka_server.yml");
        ServerRunner.createAndRunServer(EurekaClientApplication.class, "simplest_eureka_client.yml");

        //make sure dynamicMeta will work
        Thread.sleep(60 * 1000);

        Map<String, String> map = new HashMap<>();
        map.put("applicationName", "simplest-eureka-client");
        ResponseEntity<String> forEntity = template.getForEntity(
                "http://localhost:8762/actuator/info", String.class);

        logger.info("json:", forEntity.getBody());


        logger.info("you can add breakpoint here and check server page");
    }
}
