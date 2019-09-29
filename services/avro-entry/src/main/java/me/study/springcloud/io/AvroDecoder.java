/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-30T06:32:20.961+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Decoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.lang.Nullable;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j

public class AvroDecoder<T extends SpecificRecordBase> implements Decoder<T> {

    @Override
    public boolean canDecode(ResolvableType elementType, MimeType mimeType) {
        return SpecificRecordBase.class.isAssignableFrom(Objects.requireNonNull(elementType.getRawClass()));
    }

    @Override
    @Nonnull
    public List<MimeType> getDecodableMimeTypes() {
        return Collections.singletonList(AvroMediaType.AVRO_BINARY);
    }

    @Override
    @Nonnull
    public Flux<T> decode(@Nonnull Publisher<DataBuffer> inputStream, @Nonnull ResolvableType elementType, MimeType mimeType, Map<String, Object> hints) {
        Schema schema = parseSchema(elementType);
        return Flux.from(inputStream).map(
                dataBuffer -> convertToObject(schema, dataBuffer)
        );
    }

    private Schema parseSchema(ResolvableType elementType) {
        try {
            SpecificRecordBase o = (SpecificRecordBase) Objects.requireNonNull(elementType.getRawClass()).newInstance();
            return o.getSchema();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("error when parser schema", e);
            throw new IllegalStateException("error when parser schema");
        }
    }

    @Override
    @Nonnull
    public Mono<T> decodeToMono(@Nonnull Publisher<DataBuffer> inputStream, @Nonnull ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map<String, Object> hints) {

        return Mono.from(inputStream).map(
                dataBuffer -> convertToObject(parseSchema(elementType), dataBuffer)
        );
    }


    private T convertToObject(Schema finalSchema, DataBuffer dataBuffer) {
        DatumReader<T> datumReader = new SpecificDatumReader<>(finalSchema);
        org.apache.avro.io.Decoder decoder = DecoderFactory.get().binaryDecoder(dataBuffer.asInputStream(), null);
        try {
            return datumReader.read(null, decoder);
        } catch (IOException e) {
            log.error("error when read data", e);
            throw new IllegalStateException("error when parser schema");
        }
    }
}
