package me.study.zuul;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ZuulController {

    @RequestMapping(value = "/examples/tozuul")
    public String sayOk() {
        return "zuul";
    }

    @RequestMapping(value = "/examples/tozuul2")
    public String sayOk2() {
        return "zuul2";
    }
}
