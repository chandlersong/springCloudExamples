/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-22T20:58:44.587+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.services.ok;

import me.study.springcloud.io.AvroMessageArrayConverter;
import me.study.springcloud.io.AvroMessageConverter;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import static me.study.springcloud.io.AvroMediaType.AVRO_BINARY;
import static me.study.springcloud.io.AvroMediaType.AVRO_JSON;

@SpringBootApplication
@EnableDiscoveryClient
@Configuration
public class OKServicesApplication {

    @Value("${ok-service-meta.clientId:hello}")
    private String clientId;

    public static void main(String[] args) {
        SpringApplication.run(OKServicesApplication.class, args);
    }


    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
                = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix(String.format("%1$s receive request:", clientId));
        return filter;
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
    public AvroMessageConverter<? extends SpecificRecordBase> createAvroJsonConverter() {
        return new AvroMessageConverter<>(
                false,
                AVRO_JSON);
    }
}
