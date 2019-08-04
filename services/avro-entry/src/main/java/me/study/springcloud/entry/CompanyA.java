/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-04T22:02:52.926+08:00
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
public class CompanyA extends Company {

    public CompanyA(String name) {
        super("A" + name);
    }

}
