package me.study.zuul;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ZuulController {

    @RequestMapping(value = "/demo/zuul/say")
    public String sayOk() {
        return "zuul";
    }
}
