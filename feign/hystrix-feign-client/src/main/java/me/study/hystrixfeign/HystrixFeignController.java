/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-08T18:39:24.139+08:00
 * LGPL licence
 *
 */

package me.study.hystrixfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HystrixFeignController {


    private FeignOkServices feignOkServices;

    @RequestMapping(method = RequestMethod.GET, value = "/hystrix_say")
    public String normalSay(){
        return feignOkServices.sayHi().execute();
    }


    @Autowired
    public void setFeignOkServices(FeignOkServices feignOkServices) {
        this.feignOkServices = feignOkServices;
    }
}
