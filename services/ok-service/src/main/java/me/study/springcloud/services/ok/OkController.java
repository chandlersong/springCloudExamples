/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-22T20:58:44.587+08:00
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
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Address> avroBinary(@RequestBody User user) {
        return ResponseEntity.ok(user.getAddress());
    }

    @PostMapping(value = "/avroJson", consumes = AvroMediaType.AVRO_JSON_VALUE, produces = AvroMediaType.AVRO_JSON_VALUE)
    public ResponseEntity<Address> avroJSON(@RequestBody User user) {
        return ResponseEntity.ok(user.getAddress());
    }


    @GetMapping(value = "/avroList", produces = AvroMediaType.AVRO_BINARY_VALUE)
    public ResponseEntity<Address[]> avroList() {

        Address[] results = new Address[2];

        results[0] = new Address("1");
        results[1] = new Address("2");

        return ResponseEntity.ok(results);
    }
}
