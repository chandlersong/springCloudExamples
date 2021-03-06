package me.demo.springcloud.hystrix.simple;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class OKServices {

    private final RestTemplate restTemplate;

    public OKServices(RestTemplate rest) {
        this.restTemplate = rest;
    }

    @HystrixCommand(fallbackMethod = "reliable")
    public String sayOk() {
        URI uri = URI.create("http://localhost:38756/say");

        return this.restTemplate.getForObject(uri, String.class);
    }

    public String reliable() {
        return "ok services down";
    }

}
