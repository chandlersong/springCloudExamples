package me.study.ribbon.demo;

import me.demo.springcloud.services.ok.OKServicesApplication;
import me.demo.springcloud.utils.MulitAssert;
import me.demo.springcloud.utils.RestTemplateWrapper;
import me.demo.springcloud.utils.ServerRunner;
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
    public void runHelloWorldStatic() {

        ServerRunner.createAndRunServer(RibbonSimpleApplication.class, "simple_static_ribbon_service.yml"); // it must be the first to start
        ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_1.yml");
        ServerRunner.createAndRunServer(OKServicesApplication.class, "ok_services_client_2.yml");


        MulitAssert.assertMulitpleTimes(100, o -> {
            Assert.assertThat(template.doGet("/hi"), anyOf(is("client1"), is("client2")));
        });
        logger.info("stop");

    }


}
