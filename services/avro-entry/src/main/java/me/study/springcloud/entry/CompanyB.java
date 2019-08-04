/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-04T22:02:52.930+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.entry;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyB extends Company {

    public CompanyB(String name) {
        super("B" + name);
    }

}
