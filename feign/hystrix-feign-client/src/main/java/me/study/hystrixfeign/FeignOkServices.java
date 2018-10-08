/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-08T22:12:54.953+08:00
 * LGPL licence
 *
 */

package me.study.hystrixfeign;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rx.Observable;

@FeignClient(name = "okService", url = "${feign-meta.url:http://localhost:8081}", fallback = FeignOkServices.FeignOkServicesFallBack.class)
@Component
public interface FeignOkServices {

    @RequestMapping(method = RequestMethod.GET, value = "/say")
    HystrixCommand<String> sayHi();


    @RequestMapping(method = RequestMethod.GET, value = "/say")
    Observable<String> sayHiObservable();

    @Component
    class FeignOkServicesFallBack implements FeignOkServices {


        @Override
        public HystrixCommand<String> sayHi() {
            return new HystrixCommand<String>(HystrixCommandGroupKey.Factory.asKey("FeignTest")) {

                @Override
                protected String run() {
                    return "fallback";
                }
            };
        }

        @Override
        public Observable<String> sayHiObservable() {
            return Observable.from(new String[]{"fallback"});
        }
    }

}
