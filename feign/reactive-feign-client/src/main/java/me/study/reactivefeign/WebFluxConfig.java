/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-10-01T21:16:36.813+08:00
 * LGPL licence
 *
 */

package me.study.reactivefeign;

import me.study.springcloud.io.AvroDecoder;
import me.study.springcloud.io.AvroEncoder;
import me.study.springcloud.io.AvroMessageArrayConverter;
import me.study.springcloud.io.AvroMessageConverter;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import static me.study.springcloud.io.AvroMediaType.AVRO_BINARY;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {


    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        CodecConfigurer.CustomCodecs customCodecs = configurer.customCodecs();
        customCodecs.decoder(createAvroCoder());
        customCodecs.encoder(createAvroEncoder());
    }


    @Bean
    public AvroMessageArrayConverter<? extends SpecificRecordBase> createAvroListConverter() {
        return new AvroMessageArrayConverter<>(
                true,
                AVRO_BINARY);
    }

    @Bean
    public AvroMessageConverter<? extends SpecificRecordBase> createAvroBinaryConverter() {
        return new AvroMessageConverter<>(
                true,
                AVRO_BINARY);
    }


    @Bean
    public AvroEncoder<SpecificRecordBase> createAvroEncoder() {
        return new AvroEncoder<>();
    }

    @Bean
    public AvroDecoder<SpecificRecordBase> createAvroCoder() {
        return new AvroDecoder<>();
    }
}
