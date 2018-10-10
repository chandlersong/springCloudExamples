/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-11T07:16:56.909+08:00
 * LGPL licence
 *
 */

package me.demo.springcloud.utils;

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
}
