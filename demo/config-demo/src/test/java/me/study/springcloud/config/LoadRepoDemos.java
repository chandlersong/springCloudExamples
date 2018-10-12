/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-12T21:24:04.640+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.config;

import me.demo.springcloud.utils.RestRequest;
import me.demo.springcloud.utils.ServerRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoadRepoDemos {

    private static final Logger logger = getLogger(LoadRepoDemos.class);

    @Test
    public void testPlainText() {

        ServerRunner.createAndRunServer(SimpleConfigServer.class, "local_config_server_without_eureka.yml");
        logger.info("{}", RestRequest.get("aa/development/master/test.json"));
        logger.info("{}", RestRequest.get("aa/dev/master/test.json"));
        logger.info("stop");
    }
}
