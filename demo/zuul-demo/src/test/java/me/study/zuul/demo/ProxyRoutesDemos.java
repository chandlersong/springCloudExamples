package me.study.zuul.demo;

import me.demo.springcloud.utils.RestTemplateWrapper;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.services.ok.OKServicesApplication;
import me.study.zuul.SimpleZuulApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProxyRoutesDemos {

    private static final Logger logger = getLogger(ProxyRoutesDemos.class);

    private RestTemplateWrapper template = new RestTemplateWrapper();

    /**
     * 1. the configuration in above will override those after
     * 2. in the configuration, the  to-service-3, the request will be route the client3 ,but client 3 will receive
     *    /X/client3/say
     */
    @Test
    public void runExampleWithEureka() {

        ServerRunner.createAndRunServer(SimpleZuulApplication.class, "route_configuration_demos/simple_zuul_service.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "route_configuration_demos/ok_services_client_1.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "route_configuration_demos/ok_services_client_2.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "route_configuration_demos/ok_services_client_3.yml");

        Assert.assertThat(template.doGet("/demo/say"), is("client1"));
        Assert.assertThat(template.doGet("/demo/abc/say"), is("client2"));
        logger.info("stop");

    }
}
