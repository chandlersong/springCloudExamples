/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-30T06:32:20.977+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.services.ok;

import me.study.springcloud.io.AvroDecoder;
import me.study.springcloud.io.AvroEncoder;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.DecoderHttpMessageReader;
import org.springframework.http.codec.EncoderHttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {


    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.customCodecs().reader(createAvroReader());
        configurer.customCodecs().writer(createAvroWriter());
    }

    @Bean
    public EncoderHttpMessageWriter<SpecificRecordBase> createAvroWriter() {
        return new EncoderHttpMessageWriter<>(new AvroEncoder<>());
    }

    @Bean
    public DecoderHttpMessageReader<SpecificRecordBase> createAvroReader() {
        return new DecoderHttpMessageReader<>(new AvroDecoder<>());
    }
}
