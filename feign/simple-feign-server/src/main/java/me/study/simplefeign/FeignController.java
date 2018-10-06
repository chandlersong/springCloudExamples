package me.study.simplefeign;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    private FeignOkServices feignOkServices;

    @RequestMapping(value = "/sayHi")
    public String sayHi(){
        return  feignOkServices.sayHi();
    }



}
