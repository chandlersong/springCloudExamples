/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-22T22:56:07.091+08:00
 * LGPL licence
 *
 */

package me.study.springcloud;

import com.google.common.base.Charsets;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.oauth2.OAuth2ApplicationServer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class OAuth2Demos {

    private static final Logger logger = getLogger(OAuth2Demos.class);

    @Test
    public void OAuth2Examples() throws IOException, JSONException {
        ServerRunner.createAndRunServer(OAuth2ApplicationServer.class, "oauth2_simple_sever.yml");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode("password");
        logger.info("password is {}", encodePassword);
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://localhost:8080/oauth/token");
        post.setHeader("authorization", String.format("Basic %1s", new String(Base64.encodeBase64("client:secret".getBytes()))));
        post.setHeader("content-type", "application/x-www-form-urlencoded");
        post.setEntity(new StringEntity(String.format("username=user&password=%1$s&grant_type=password&&scope=all", "password")));
        HttpResponse response = client.execute(post);


        HeaderIterator headerIterator = response.headerIterator();

        while (headerIterator.hasNext()) {
            Header header = headerIterator.nextHeader();

            logger.info("header key is {},header value is{}", header.getName(), header.getValue());
        }

        logger.info("status is {}", response.getStatusLine().getStatusCode());
        String str = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
        JSONObject json = new JSONObject(str);
        logger.info("access_token is {}", json.get("access_token"));
        logger.info("stop");
    }
}
