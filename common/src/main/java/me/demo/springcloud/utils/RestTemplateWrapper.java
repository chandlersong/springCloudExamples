package me.demo.springcloud.utils;

import org.slf4j.Logger;
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
        pathFormat = "http://" + host + ":" + String.valueOf(port) + "/%1$s";
    }

    public String doGet(String path) {
        String callPath = String.format(pathFormat, path);
        logger.debug("path {}", callPath);
        return restTemplate.getForEntity(callPath, String.class).getBody();
    }

    public String doGet(String format, Object... args) {
        return doGet(String.format(format, args));
    }
}
