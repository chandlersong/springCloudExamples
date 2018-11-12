/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-11-12T23:14:26.353+08:00
 * LGPL licence
 *
 */

package me.study.springcloud;

import com.google.common.base.Charsets;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.jwt.JwtApplicationServer;
import me.study.springcloud.jwt.resource.JwtResourceApplicationServer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class JWTDemos {

    private static final Logger logger = getLogger(JWTDemos.class);

    @Test
    public void testSimpleDemo() throws IOException {
        ServerRunner.createAndRunServer(JwtApplicationServer.class, "jwt_simple_sever.yml");
        ServerRunner.createAndRunServer(JwtResourceApplicationServer.class, "jwt_resource_sever.yml");

        HttpClient client = HttpClientBuilder.create().build();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode("password");
        logger.info("password is {}", encodePassword);
        HttpPost post = new HttpPost("http://localhost:8090/oauth/token");

        post.setHeader("authorization", String.format("Basic %1s", new String(Base64.encodeBase64("client:secret".getBytes()))));
        post.setHeader("content-type", "application/x-www-form-urlencoded");
        post.setEntity(new StringEntity(String.format("username=user&password=%1$s&grant_type=password&&scope=all", "password")));
        HttpResponse response = client.execute(post);


        HeaderIterator headerIterator = response.headerIterator();

        while (headerIterator.hasNext()) {
            Header header = headerIterator.nextHeader();

            logger.info("header key is {},header value is{}", header.getName(), header.getValue());
        }
        String responseContext = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
        logger.info("response context {}", responseContext);
    }
}
