package me.study.ribbon.demo;

import me.demo.springcloud.services.ok.OKServicesApplication;
import me.demo.springcloud.utils.MulitAssert;
import me.demo.springcloud.utils.RestTemplateWrapper;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.eureka.server.EurekaServerApplication;
import me.study.springcloud.ribbon.RibbonSimpleApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleRibbonDemo {

    private static final Logger logger = getLogger(SimpleRibbonDemo.class);

    private RestTemplateWrapper template = new RestTemplateWrapper();

    @Test
    public void runExampleWithOutEureka() {

        ServerRunner.createAndRunServer(RibbonSimpleApplication.class, "simple_ribbon_service_without_eureka.yml"); // it must be the first to start
        ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_without_eureka_1.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_without_eureka_2.yml");


        MulitAssert.assertMulitpleTimes(100, o -> Assert.assertThat(template.doGet("/hi"), anyOf(is("client1"), is("client2"))));
        logger.info("stop");

    }


    /**
     * the url in the ribbon client should be same as server id.
     * in this example:
     * the url is http://ok-service/say
     * and the server in eureka should be ok-service
     *
     * @throws InterruptedException
     */
    @Test
    public void runExampleWithEureka() throws InterruptedException {

        ServerRunner.createAndRunServer(EurekaServerApplication.class);
        ServerRunner.createAndRunServer(RibbonSimpleApplication.class, "simple_ribbon_service.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_1.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_2.yml");


        Thread.sleep(60 * 1000);
        MulitAssert.assertMulitpleTimes(100, o -> Assert.assertThat(template.doGet("/hi"), anyOf(is("client1"), is("client2"))));
        logger.info("stop");

    }


    /**
     * this case seems not working.Because retry not support  connection refuse
     * https://github.com/spring-cloud/spring-cloud-netflix/issues/2927
     *
     * @throws InterruptedException
     */
    public void runExampleWithEurekaAndOneDwon() throws InterruptedException {

        ServerRunner.createAndRunServer(EurekaServerApplication.class);
        ServerRunner.createAndRunServer(RibbonSimpleApplication.class, "simple_ribbon_service.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_1.yml");
        ServerRunner okServer2 = ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_2.yml");


        Thread.sleep(60 * 1000);
        MulitAssert.assertMulitpleTimes(100, o -> Assert.assertThat(template.doGet("/hi"), anyOf(is("client1"), is("client2"))));

        okServer2.stop();
        MulitAssert.assertMulitpleTimes(100, o -> Assert.assertEquals("client1", template.doGet("/hi")));

        logger.info("stop");

    }
}
