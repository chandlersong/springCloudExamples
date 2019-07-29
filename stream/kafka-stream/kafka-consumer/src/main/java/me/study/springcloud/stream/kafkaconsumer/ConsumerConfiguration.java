/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-07-29T23:22:54.224+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.stream.kafkaconsumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.schema.avro.AvroSchemaMessageConverter;
import org.springframework.cloud.stream.schema.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.util.MimeType;

@Configuration
public class ConsumerConfiguration {

    @Bean
    public MessageConverter userMessageConverter() {
        return new AvroSchemaMessageConverter(MimeType.valueOf("avro/bytes"));
    }

    @Bean
    public SchemaRegistryClient schemaRegistryClient(@Value("${spring.cloud.stream.schemaRegistryClient.endpoint}") String endpoint) {
        ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
        client.setEndpoint(endpoint);
        return client;
    }
}
