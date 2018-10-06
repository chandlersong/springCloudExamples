package me.demo.springcloud.utils;

public class RestRequest {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8080;


    public static String get(String host, int port, String url) {
        return (new RestTemplateWrapper(host, port)).doGet(url);
    }

    public static String get(String url) {
        return get(DEFAULT_HOST, DEFAULT_PORT, url);
    }
}
