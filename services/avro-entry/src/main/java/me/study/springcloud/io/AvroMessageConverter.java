/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-14T14:20:48.707+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class AvroMessageConverter<T extends SpecificRecordBase> extends AbstractHttpMessageConverter<T> {


    private final boolean useBinaryEncoding;

    public AvroMessageConverter(boolean useBinaryEncoding, MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
        this.useBinaryEncoding = useBinaryEncoding;
    }


    @Override
    protected boolean supports(Class<?> clazz) {
        return SpecificRecordBase.class.isAssignableFrom(clazz);
    }

    @Override
    protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Schema schema;
        byte[] data;
        try {
            schema = clazz.newInstance().getSchema();
            data = IOUtils.toByteArray(inputMessage.getBody());

        } catch (InstantiationException | IllegalAccessException e) {
            log.error("error happen!!!!", e);
            throw new IllegalArgumentException();
        }
        DatumReader<T> datumReader =
                new SpecificDatumReader<>(schema);
        Decoder decoder = useBinaryEncoding ?
                DecoderFactory.get().binaryDecoder(data, null) :
                DecoderFactory.get().jsonDecoder(schema, new ByteArrayInputStream(data));
        return datumReader.read(null, decoder);
    }

    @Override
    protected void writeInternal(T t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Encoder encoder = useBinaryEncoding ?
                EncoderFactory.get().binaryEncoder(byteArrayOutputStream, null) :
                EncoderFactory.get().jsonEncoder(t.getSchema(), byteArrayOutputStream);

        DatumWriter<T> datumWriter = new SpecificDatumWriter<>(t.getSchema());
        datumWriter.write(t, encoder);

        encoder.flush();
        byteArrayOutputStream.close();
        outputMessage.getBody().write(byteArrayOutputStream.toByteArray());
    }
}
