/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-07-27T11:26:10.656+08:00
 * LGPL licence
 *
 */

package me.demo.springcloud.utils;


import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OAuth2TemplateFactory {

    private static final String DEFAULT_CLIENT_ID = "client";

    private static final String DEFAULT_TOKEN_PATH = "http://localhost:8080/oauth/token";
    private static final String DEFAULT_SECRET = "secret";
    private static final String DEFAULT_GRANT_TYPE = "password";
    private static final String DEFAULT_USER_NAME = "user";
    private static final String DEFAULT_PASSWORD = "password";
    private static final List<String> DEFAULT_SCOPES = Collections.singletonList("all");
    private String tokenPath = DEFAULT_TOKEN_PATH;

    private String clientId = DEFAULT_CLIENT_ID;
    private String secret = DEFAULT_SECRET;
    private String grantType = DEFAULT_GRANT_TYPE;
    private String username = DEFAULT_USER_NAME;
    private String password = DEFAULT_PASSWORD;


    private List<String> scopes = DEFAULT_SCOPES;

    private OAuth2ProtectedResourceDetails resource() {

        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();


        resource.setAccessTokenUri(tokenPath);
        resource.setClientId(clientId);
        resource.setClientSecret(secret);
        resource.setGrantType(grantType);
        resource.setScope(scopes);

        resource.setUsername(username);
        resource.setPassword(password);

        return resource;
    }

    public OAuth2RestTemplate createRestTemplate() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        return new OAuth2RestTemplate(resource(), new MyContext(atr));
    }

    public String getTokenPath() {
        return tokenPath;
    }

    public void setTokenPath(String tokenPath) {
        this.tokenPath = tokenPath;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    private class MyContext extends DefaultOAuth2ClientContext {

        MyContext(AccessTokenRequest atr) {
            super(atr);
        }


        @Override
        public void setAccessToken(OAuth2AccessToken accessToken) {

            if (Objects.nonNull(accessToken) &&
                    OAuth2AccessToken.BEARER_TYPE.toLowerCase().equals(accessToken.getTokenType()) &&
                    accessToken instanceof DefaultOAuth2AccessToken) {
                DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
                token.setTokenType(OAuth2AccessToken.BEARER_TYPE);
                super.setAccessToken(token);
            }
            super.setAccessToken(accessToken);
        }
    }
}



