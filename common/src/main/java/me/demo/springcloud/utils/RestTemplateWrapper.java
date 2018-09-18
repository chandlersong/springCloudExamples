package me.demo.springcloud.utils;

import org.springframework.web.client.RestTemplate;

public class RestTemplateWrapper {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String pathFormat;

    public RestTemplateWrapper() {
        this(8080);
    }

    public RestTemplateWrapper(int port) {
        this("localhost", port);
    }

    public RestTemplateWrapper(String host, int port) {
        pathFormat = "http://" + host + ":" + String.valueOf(port) + "/%1$s";
    }

    public String doGet(String path) {
        return restTemplate.getForEntity(String.format(pathFormat, path), String.class).getBody();
    }
}
