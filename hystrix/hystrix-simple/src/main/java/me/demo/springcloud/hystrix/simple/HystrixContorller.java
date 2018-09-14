package me.demo.springcloud.hystrix.simple;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HystrixContorller {

    @Autowired
    private OKServices okServices;

    @RequestMapping("/mustSay")
    public String toRead() {
        return okServices.sayOk();
    }
}
