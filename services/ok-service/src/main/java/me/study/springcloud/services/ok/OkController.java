/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-14T14:20:48.718+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.services.ok;


import lombok.extern.slf4j.Slf4j;
import me.study.springcloud.Address;
import me.study.springcloud.User;
import me.study.springcloud.io.AvroMediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OkController {

    @Value("${ok-service-meta.clientId:hello}")
    private String clientId;

    @RequestMapping(value = "/say")
    public String sayOk() {
        return clientId;
    }


    @PostMapping(value = "/avroBinary", consumes = AvroMediaType.AVRO_BINARY_VALUE, produces = AvroMediaType.AVRO_BINARY_VALUE)
    public ResponseEntity<Address> avro(@RequestBody User user) {
        return ResponseEntity.ok(user.getAddress());
    }
}
