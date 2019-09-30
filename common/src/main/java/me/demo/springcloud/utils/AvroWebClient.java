/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-30T21:52:06.644+08:00
 * LGPL licence
 *
 */

package me.demo.springcloud.utils;

import me.study.springcloud.io.AvroDecoder;
import me.study.springcloud.io.AvroEncoder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import static me.study.springcloud.io.AvroMediaType.AVRO_BINARY_VALUE;

public class AvroWebClient {

    public static WebClient createAvroWebClient(String baseUrl) {
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
                .baseUrl(baseUrl)
                .build();

    }

}
