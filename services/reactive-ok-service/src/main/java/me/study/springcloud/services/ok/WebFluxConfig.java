/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-30T20:55:36.041+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.services.ok;

import me.study.springcloud.io.AvroDecoder;
import me.study.springcloud.io.AvroEncoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {


    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        CodecConfigurer.CustomCodecs customCodecs = configurer.customCodecs();
        customCodecs.decoder(new AvroDecoder<>());
        customCodecs.encoder(new AvroEncoder<>());
    }
}
