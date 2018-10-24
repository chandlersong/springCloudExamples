/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-24T22:50:00.422+08:00
 * LGPL licence
 *
 */

package me.study.springcloud;

import com.google.common.base.Charsets;
import me.demo.springcloud.utils.ServerRunner;
import me.study.springcloud.oauth2.OAuth2ApplicationServer;
import me.study.springcloud.oauth2.resource.ResourceApplicationServer;
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
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class OAuth2Demos {

    private static final Logger logger = getLogger(OAuth2Demos.class);

    @Test
    public void OAuth2Examples() throws IOException, JSONException {
        ServerRunner.createAndRunServer(OAuth2ApplicationServer.class, "oauth2_simple_sever.yml");
        ServerRunner.createAndRunServer(ResourceApplicationServer.class, "oauth2_resource_sever.yml");

        HttpClient client = HttpClientBuilder.create().build();

        HttpGet resource = new HttpGet("http://localhost:8081/resource");
        HttpResponse resourceResource = client.execute(resource);

        Assert.assertEquals(401, resourceResource.getStatusLine().getStatusCode());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode("password");
        logger.info("password is {}", encodePassword);
        HttpPost post = new HttpPost("http://localhost:9080/oauth/token");
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
        String responseContext = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
        logger.info("response context {}", responseContext);

        JSONObject json = new JSONObject(responseContext);
        resource = new HttpGet(String.format("http://localhost:8081/resource?access_token=%1$s&token_type=%2$s", json.get("access_token"), json.get("token_type")));

        resourceResource = client.execute(resource);

        logger.info("status code is:{}", resourceResource.getStatusLine().getStatusCode());
        Assert.assertEquals("ok", IOUtils.toString(resourceResource.getEntity().getContent(), Charsets.UTF_8));
        logger.info("stop");
    }

    @Test
    public void OAuth2ExamplesUseTemplate() {
        ServerRunner.createAndRunServer(OAuth2ApplicationServer.class, "oauth2_simple_sever.yml");
        ServerRunner.createAndRunServer(ResourceApplicationServer.class, "oauth2_resource_sever.yml");


        OAuth2RestTemplate template = restTemplate();
        ResponseEntity<String> get = template.getForEntity("http://localhost:9081/resource", String.class);
        Assert.assertEquals("ok", get.getBody());
        logger.info("stop");
    }


    private OAuth2ProtectedResourceDetails resource() {

        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();

        List<String> scopes = new ArrayList<>(2);
        scopes.add("all");
        resource.setAccessTokenUri("http://localhost:9080/oauth/token");
        resource.setClientId("client");
        resource.setClientSecret("secret");
        resource.setGrantType("password");
        resource.setScope(scopes);

        resource.setUsername("user");
        resource.setPassword("password");

        return resource;
    }

    private OAuth2RestTemplate restTemplate() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();

        return new OAuth2RestTemplate(resource(), new DefaultOAuth2ClientContext(atr));
    }

}
