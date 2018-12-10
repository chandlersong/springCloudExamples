/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-12-10T23:37:47.779+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.jwtresource;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/health").permitAll()
                .antMatchers("/info").permitAll()
                .antMatchers("/**").hasAuthority("SCOPE_all")
                .anyRequest().authenticated()
                .and().oauth2ResourceServer().jwt();
    }
}
