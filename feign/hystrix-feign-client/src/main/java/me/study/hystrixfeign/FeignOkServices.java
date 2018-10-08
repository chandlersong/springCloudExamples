/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-08T22:01:01.740+08:00
 * LGPL licence
 *
 */

package me.study.hystrixfeign;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rx.Observable;

@FeignClient(name = "okService", url = "${feign-meta.url:http://localhost:8081}")
@Component
public interface FeignOkServices {

    @RequestMapping(method = RequestMethod.GET, value = "/say")
    HystrixCommand<String> sayHi();


    @RequestMapping(method = RequestMethod.GET, value = "/say")
    Observable<String> sayHiObservable();
}
