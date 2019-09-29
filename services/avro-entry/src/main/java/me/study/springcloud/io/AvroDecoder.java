/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-29T23:03:00.272+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.io;

import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Decoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AvroDecoder<T extends SpecificRecordBase> implements Decoder<T> {

    @Override
    public boolean canDecode(ResolvableType elementType, MimeType mimeType) {
        return SpecificRecordBase.class.isAssignableFrom(Objects.requireNonNull(elementType.getRawClass()));
    }

    @Override
    public List<MimeType> getDecodableMimeTypes() {
        return Collections.singletonList(AvroMediaType.AVRO_BINARY);
    }

    @Override
    public Flux<T> decode(Publisher<DataBuffer> inputStream, ResolvableType elementType, MimeType mimeType, Map<String, Object> hints) {
        Schema schema = null;
        try {
            SpecificRecordBase o = (SpecificRecordBase) elementType.getRawClass().newInstance();
            schema = o.getSchema();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Schema finalSchema = schema;
        return Flux.from(inputStream).map(
                dataBuffer -> convertToObject(finalSchema, dataBuffer)
        );
    }

    @Override
    public Mono<T> decodeToMono(Publisher<DataBuffer> inputStream, ResolvableType elementType, MimeType mimeType, Map<String, Object> hints) {
        Schema schema = null;
        try {
            SpecificRecordBase o = (SpecificRecordBase) elementType.getRawClass().newInstance();
            schema = o.getSchema();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Schema finalSchema = schema;
        return Mono.from(inputStream).map(
                dataBuffer -> convertToObject(finalSchema, dataBuffer)
        );
    }


    private T convertToObject(Schema finalSchema, DataBuffer dataBuffer) {
        DatumReader<T> datumReader = new SpecificDatumReader<>(finalSchema);
        org.apache.avro.io.Decoder decoder = DecoderFactory.get().binaryDecoder(dataBuffer.asInputStream(), null);
        try {
            return datumReader.read(null, decoder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
