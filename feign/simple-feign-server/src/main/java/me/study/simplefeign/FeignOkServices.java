package me.study.simplefeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("${simple-feign-meta.okService:OKService}")
@Component
public interface FeignOkServices {

    @RequestMapping(method = RequestMethod.GET, value = "/say")
    String sayHi();
}
