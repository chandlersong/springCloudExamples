/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-12-13T20:59:18.213+08:00
 * LGPL licence
 *
 */

package me.study.springcloud;

import com.google.common.base.Charsets;
import me.demo.springcloud.utils.OAuth2TemplateFactory;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.jwt.JwtApplicationServer;
import me.study.springcloud.jwtresource.JwtResourceApplicationServer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

public class JWTDemos {

    private static final Logger logger = getLogger(JWTDemos.class);

    private OAuth2TemplateFactory templateFactory = new OAuth2TemplateFactory();


    @Test
    public void testSimpleDemo() throws IOException, JSONException {
        ServerRunner.createAndRunServer(JwtApplicationServer.class, "jwt_simple_sever.yml");
        ServerRunner.createAndRunServer(JwtResourceApplicationServer.class, "jwt_resource_sever.yml");

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet resource = new HttpGet("http://localhost:8081/resource");
        HttpResponse resourceResource = client.execute(resource);

        Assert.assertEquals(401, resourceResource.getStatusLine().getStatusCode());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode("password");
        logger.info("password is {}", encodePassword);
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
        String responseContext = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
        logger.info("response context {}", responseContext);
        JSONObject auth = new JSONObject(responseContext);
        String token = auth.get("access_token").toString();
        logger.info("key:{}", token);
        logger.info("header :{}", new String(Base64.decodeBase64(token.split("\\.")[0])));
        JSONObject payload = new JSONObject(new String(Base64.decodeBase64(token.split("\\.")[1])));
        logger.info("user info:{}", payload.toString());
        logger.info("expire date:{}", new Date(payload.getLong("exp") * 1000));

        logger.info("taller:{}", new String(Base64.decodeBase64(token.split("\\.")[2])));

        HttpGet authResource = new HttpGet("http://localhost:8081/resource");
        authResource.setHeader("Authorization", String.format("Bearer %1s", token));
        HttpResponse withAuth = client.execute(authResource);
        logger.info("response code:{}", withAuth.getStatusLine().getStatusCode());
        logger.info("response body:{}", IOUtils.toString(withAuth.getEntity().getContent(), Charsets.UTF_8));
    }

    @Test
    public void JwtTemplate() {
        ServerRunner.createAndRunServer(JwtApplicationServer.class, "jwt_simple_sever.yml");
        ServerRunner.createAndRunServer(JwtResourceApplicationServer.class, "jwt_resource_sever.yml");

        ResponseEntity<String> get;
        OAuth2RestTemplate client1 = templateFactory.createRestTemplate();
        get = client1.getForEntity("http://localhost:8081/resource", String.class);
        Assert.assertEquals("ok", get.getBody());

        get = client1.getForEntity("http://localhost:8081/user", String.class);

        Assert.assertEquals("user", get.getBody());
        logger.info("stop");

        templateFactory.setClientId("client2");
        templateFactory.setSecret("abc");
        templateFactory.setScopes(Collections.singletonList("read"));
        OAuth2RestTemplate client2 = templateFactory.createRestTemplate();


        try {
            client2.getForEntity("http://localhost:8081/resource", String.class);
        } catch (Exception e) {
            // the error code is 403
            logger.error("scope not correct:", e);
        }
    }

    @Test
    public void printToken() throws Exception {
        ServerRunner.createAndRunServer(JwtApplicationServer.class, "jwt_simple_sever.yml");
        ServerRunner.createAndRunServer(JwtResourceApplicationServer.class, "jwt_resource_sever.yml");

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet resource = new HttpGet("http://localhost:8081/resource");
        HttpResponse resourceResource = client.execute(resource);

        Assert.assertEquals(401, resourceResource.getStatusLine().getStatusCode());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode("password");
        logger.info("password is {}", encodePassword);
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
        String responseContext = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
        logger.info("response context {}", responseContext);

        JSONObject auth = new JSONObject(responseContext);
        String token = auth.get("access_token").toString();
        logger.info("key:{}", token);
        logger.info("header :{}", new String(Base64.decodeBase64(token.split("\\.")[0])));
        logger.info("user info:{}", new String(Base64.decodeBase64(token.split("\\.")[1])));
        logger.info("taller:{}", new String(Base64.decodeBase64(token.split("\\.")[2])));


    }


    @Test
    public void refreshToken() throws Exception {
        ServerRunner.createAndRunServer(JwtApplicationServer.class, "jwt_simple_sever.yml");
        ServerRunner.createAndRunServer(JwtResourceApplicationServer.class, "jwt_resource_sever.yml");

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet resource = new HttpGet("http://localhost:8081/resource");
        HttpResponse resourceResource = client.execute(resource);

        Assert.assertEquals(401, resourceResource.getStatusLine().getStatusCode());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode("password");
        logger.info("password is {}", encodePassword);
        HttpPost post = new HttpPost("http://localhost:8080/oauth/token");

        post.setHeader("authorization", String.format("Basic %1s", new String(Base64.encodeBase64("client:secret".getBytes()))));
        post.setHeader("content-type", "application/x-www-form-urlencoded");
        post.setEntity(new StringEntity(String.format("username=user&password=%1$s&grant_type=password&&scope=all", "password")));

        logger.info("now instant before first post:{},instant", Instant.now(Clock.systemDefaultZone()));
        HttpResponse response = client.execute(post);
        logger.info("now instant after first post:{},instant", Instant.now(Clock.systemDefaultZone()));
        String responseContext = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
        logger.info("response context {}", responseContext);

        JSONObject auth = new JSONObject(responseContext);
        String token = auth.get("access_token").toString();
        String refreshToken = auth.get("refresh_token").toString();
        JSONObject payload = new JSONObject(new String(Base64.decodeBase64(token.split("\\.")[1])));


        HttpGet authResource = new HttpGet("http://localhost:8081/resource");
        authResource.setHeader("Authorization", String.format("Bearer %1s", token));
        HttpResponse withAuth = client.execute(authResource);
        logger.info("response code:{}", withAuth.getStatusLine().getStatusCode());
        logger.info("response body:{}", IOUtils.toString(withAuth.getEntity().getContent(), Charsets.UTF_8));


        Thread.sleep(10 * 1000);
        logger.info("expire date:{},now is {}", new Date(payload.getLong("exp") * 1000), new Date());
        logger.info("expire instant:{}", Instant.ofEpochSecond(payload.getLong("exp")));
        logger.info("now instant:{},instant", Instant.now(Clock.systemUTC()));

        authResource = new HttpGet("http://localhost:8081/resource");
        authResource.setHeader("Authorization", String.format("Bearer %1s", token));
        HttpResponse second = client.execute(authResource);
        logger.info("second response code:{}", second.getStatusLine().getStatusCode());
        Assert.assertEquals(401, second.getStatusLine().getStatusCode());
        logger.info("second response body:{}", IOUtils.toString(second.getEntity().getContent(), Charsets.UTF_8));


        HttpPost refreshPost = new HttpPost("http://localhost:8080/oauth/token");

        refreshPost.setHeader("authorization", String.format("Basic %1s", new String(Base64.encodeBase64("client:secret".getBytes()))));
        refreshPost.setHeader("content-type", "application/x-www-form-urlencoded");
        refreshPost.setEntity(new StringEntity(String.format("grant_type=refresh_token&refresh_token=%1s", refreshToken)));
        HttpResponse refreshResponse = client.execute(refreshPost);

        logger.info("refresh response code:{}", refreshResponse.getStatusLine().getStatusCode());
        logger.info("refresh response body:{}", IOUtils.toString(refreshResponse.getEntity().getContent(), Charsets.UTF_8));

    }
}
