/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-12-11T21:43:15.928+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.jwtresource;

import org.slf4j.Logger;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class ResourceController {

    private static final Logger logger = getLogger(ResourceController.class);

    @GetMapping("/resource")
    public String securedResource() {
        return "ok";
    }

    @GetMapping("/user")
    public String securedResource(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;

        token.getToken().getClaims().forEach((key, value) -> logger.info("key:{},value:{}", key, value));

        return token.getToken().getClaims().get("user_name").toString();
    }

}
