/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-22T22:56:07.064+08:00
 * LGPL licence
 *
 */

package me.demo.springcloud.utils;

import org.springframework.http.ResponseEntity;

public class RestRequest {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8080;


    public static String get(String host, int port, String url) {
        return (new RestTemplateWrapper(host, port)).doGet(url);
    }

    public static String get(int port, String url) {
        return (new RestTemplateWrapper(DEFAULT_HOST, port)).doGet(url);
    }

    public static String get(String url) {
        return get(DEFAULT_HOST, DEFAULT_PORT, url);
    }


    public static ResponseEntity<String> getResponse(String url) {
        return getResponse(DEFAULT_HOST, DEFAULT_PORT, url);
    }

    public static ResponseEntity<String> getResponse(int port, String url) {
        return getResponse(DEFAULT_HOST, port, url);
    }

    public static ResponseEntity<String> getResponse(String host, int port, String url) {
        return (new RestTemplateWrapper(host, port)).doGetResponse(url);
    }
}
