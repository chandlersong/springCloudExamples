package me.study.zuul.demo;

import me.demo.springcloud.utils.MulitAssert;
import me.demo.springcloud.utils.RestTemplateWrapper;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.eureka.server.EurekaServerApplication;
import me.study.springcloud.services.ok.OKServicesApplication;
import me.study.zuul.SimpleZuulApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleZuulDemos {

    private static final Logger logger = getLogger(SimpleZuulDemos.class);

    private RestTemplateWrapper template = new RestTemplateWrapper();

    @Test
    public void testRunZuulWithoutEureka() {
        logger.info("run zuul example without Eureka");
        ServerRunner.createAndRunServer(SimpleZuulApplication.class, "simple_zuul_without_eureka/simple_zuul_service.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "simple_zuul_without_eureka/ok_services_client_without_eureka_1.yml");

        Assert.assertEquals("client1", template.doGet("/demo/say"));
    }

    @Test
    public void runExampleWithEureka() throws InterruptedException {

        ServerRunner.createAndRunServer(EurekaServerApplication.class);
        ServerRunner.createAndRunServer(SimpleZuulApplication.class, "simple_zuul_with_eureka/simple_zuul_service.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "simple_zuul_with_eureka/ok_services_client_1.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "simple_zuul_with_eureka/ok_services_client_2.yml");

        Thread.sleep(60 * 1000);
        MulitAssert.assertMulitpleTimes(100, o -> Assert.assertThat(template.doGet("/demo/say"), anyOf(is("client1"), is("client2"))));
        logger.info("stop");

    }
}
