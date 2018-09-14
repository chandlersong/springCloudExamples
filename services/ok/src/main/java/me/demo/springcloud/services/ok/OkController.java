package me.demo.springcloud.services.ok;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OkController {

    @RequestMapping(value = "/say")
    public String sayOk() {
        return "ok";
    }

}
