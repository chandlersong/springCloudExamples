/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-29T23:03:00.276+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.io;

import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Encoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AvroEncoder<T extends SpecificRecordBase> implements Encoder<T> {


    @Override
    public boolean canEncode(ResolvableType elementType, MimeType mimeType) {
        return SpecificRecordBase.class.isAssignableFrom(Objects.requireNonNull(elementType.getRawClass()));
    }

    @Override
    public Flux<DataBuffer> encode(Publisher<? extends T> inputStream, DataBufferFactory bufferFactory, ResolvableType elementType, MimeType mimeType, Map<String, Object> hints) {
        return Flux
                .from(inputStream)
                .map(message -> encodeMessage(message, bufferFactory, !(inputStream instanceof Mono)));
    }

    private DataBuffer encodeMessage(T message, DataBufferFactory bufferFactory, boolean streaming) {
        DataBuffer buffer = bufferFactory.allocateBuffer();
        OutputStream outputStream = buffer.asOutputStream();
        try {
            org.apache.avro.io.Encoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
            DatumWriter<T> datumWriter = new SpecificDatumWriter<>(message.getSchema());
            datumWriter.write(message, encoder);
            encoder.flush();
            return buffer;
        } catch (IOException ex) {
            throw new IllegalStateException("Unexpected I/O error while writing to data buffer", ex);
        }
    }

    @Override
    public List<MimeType> getEncodableMimeTypes() {
        return Collections.singletonList(AvroMediaType.AVRO_BINARY);
    }
}
