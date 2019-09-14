/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-14T14:20:48.723+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.services.ok;

import me.study.springcloud.io.AvroMessageConverter;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static me.study.springcloud.io.AvroMediaType.AVRO_BINARY;

@SpringBootApplication
@EnableDiscoveryClient
@Configuration
public class OKServicesApplication implements WebMvcConfigurer {

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
    public AvroMessageConverter<SpecificRecordBase> createAvroConverter() {
        return new AvroMessageConverter<>(
                true,
                AVRO_BINARY);
    }
}
