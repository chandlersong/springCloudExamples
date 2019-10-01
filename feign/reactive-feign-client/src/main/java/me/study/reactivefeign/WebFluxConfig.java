/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-10-01T22:29:36.542+08:00
 * LGPL licence
 *
 */

package me.study.reactivefeign;

import me.study.springcloud.io.AvroDecoder;
import me.study.springcloud.io.AvroEncoder;
import me.study.springcloud.io.AvroMessageArrayConverter;
import me.study.springcloud.io.AvroMessageConverter;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import static me.study.springcloud.io.AvroMediaType.AVRO_BINARY;
import static me.study.springcloud.io.AvroMediaType.AVRO_BINARY_VALUE;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

    @Autowired
    private LoadBalancerClient lbClient;

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

    @Bean
    public WebClient.Builder createWebClient() {
        ExchangeStrategies strategies = ExchangeStrategies.
                builder()
                .codecs(clientCodecConfigurer -> {
                    CodecConfigurer.CustomCodecs customCodecs = clientCodecConfigurer.customCodecs();
                    customCodecs.encoder(new AvroEncoder<>());
                    customCodecs.decoder(new AvroDecoder<>());
                })
                .build();
        return WebClient.builder()
                .defaultHeaders(headers -> {
                    headers.add(HttpHeaders.ACCEPT, AVRO_BINARY_VALUE);
                    headers.add(HttpHeaders.CONTENT_TYPE, AVRO_BINARY_VALUE);
                })
                .exchangeStrategies(strategies)
                .baseUrl("http://ReactiveOKService")
                .filter((new LoadBalancerExchangeFilterFunction(lbClient)));
    }
}
