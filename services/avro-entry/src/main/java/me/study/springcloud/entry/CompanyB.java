/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-04T16:56:03.742+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.entry;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CompanyB extends Company {

    public CompanyB(String name) {
        super("B" + name);
    }

}
