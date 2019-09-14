/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-14T16:29:18.240+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.services.ok;

import me.study.springcloud.io.AvroMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static me.study.springcloud.io.AvroMediaType.AVRO_BINARY;
import static me.study.springcloud.io.AvroMediaType.AVRO_JSON;


@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {

        converters.add(0, new AvroMessageConverter<>(
                true,
                AVRO_BINARY));
        converters.add(1, new AvroMessageConverter<>(
                false,
                AVRO_JSON));
    }
}
