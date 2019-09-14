/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-14T14:20:48.745+08:00
 * LGPL licence
 *
 */

package me.demo.springcloud.utils;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.slf4j.LoggerFactory.getLogger;

public class RestTemplateWrapper {

    private static final Logger logger = getLogger(RestTemplateWrapper.class);
    private final RestTemplate restTemplate = new RestTemplate();

    private final String pathFormat;

    public RestTemplateWrapper() {
        this(8080);
    }

    public RestTemplateWrapper(int port) {
        this("localhost", port);
    }

    public RestTemplateWrapper(String host, int port) {
        pathFormat = "http://" + host + ":" + port + "/%1$s";
    }

    public String doGet(String path) {
        return doGetResponse(path).getBody();
    }

    public ResponseEntity<String> doGetResponse(String path) {
        String callPath = String.format(pathFormat, path);
        logger.trace("path {}", callPath);
        return restTemplate.getForEntity(callPath, String.class);
    }


}
