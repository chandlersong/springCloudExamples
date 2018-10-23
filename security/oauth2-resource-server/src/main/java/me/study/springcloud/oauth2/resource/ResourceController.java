/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-23T23:56:18.432+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.oauth2.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/resource")
    public String securedResource() {
        return "ok";
    }

}
