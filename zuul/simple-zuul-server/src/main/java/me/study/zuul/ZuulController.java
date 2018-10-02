package me.study.zuul;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ZuulController {

    @RequestMapping(value = "/timeout")
    public String sayOk() {
        return "zuul";
    }
}
