/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-14T16:29:18.224+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.io;

import com.google.common.base.Charsets;
import org.springframework.http.MediaType;

public interface AvroMediaType {

    MediaType AVRO_BINARY = new MediaType("application", "avro", Charsets.UTF_8);
    MediaType AVRO_BINARY_STAR = new MediaType("application", "*+avro", Charsets.UTF_8);
    String AVRO_BINARY_VALUE = "application/avro";
    String AVRO_BINARY_START_VALUE = "application/*+avro";
    MediaType AVRO_JSON = new MediaType("application", "avro+json", Charsets.UTF_8);
    String AVRO_JSON_VALUE = "application/avro+json";
    MediaType AVRO_JSON_STAR = new MediaType("application", "*+avro+json", Charsets.UTF_8);
}
