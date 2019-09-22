/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-09-22T20:58:44.571+08:00
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

import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AvroMessageArrayConverter<T extends SpecificRecordBase> extends AbstractHttpMessageConverter<T[]> {


    private final boolean useBinaryEncoding;

    public AvroMessageArrayConverter(boolean useBinaryEncoding, MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
        this.useBinaryEncoding = useBinaryEncoding;
    }


    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.isArray() && SpecificRecordBase.class.isAssignableFrom(clazz.getComponentType());
    }

    @Override
    protected T[] readInternal(@NotNull Class<? extends T[]> clazz, @NotNull HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Schema schema;
        byte[] data;

        Class<?> componentType = clazz.getComponentType();
        try {
            schema = ((SpecificRecordBase) componentType.newInstance()).getSchema();
            data = IOUtils.toByteArray(inputMessage.getBody());
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("get initial data", e);
            throw new IllegalArgumentException();
        }
        DatumReader<T> datumReader = new SpecificDatumReader<>(schema);
        Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);
        List<T> records = new ArrayList<>();
        while (true) {
            try {
                T record = datumReader.read(null, decoder);
                records.add(record);
            } catch (EOFException e) {
                break;
            }
        }
        T[] o = (T[]) Array.newInstance(componentType, 0);
        return records.toArray(o);
    }

    @Override
    protected void writeInternal(@NotNull T[] ts, @NotNull HttpOutputMessage outputMessage) throws HttpMessageNotWritableException, IOException {

        if (ts.length == 0) {
            return;
        }

        T t = ts[0];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Encoder encoder = EncoderFactory.get().binaryEncoder(byteArrayOutputStream, null);


        DatumWriter<T> datumWriter = new SpecificDatumWriter<>(t.getSchema());

        for (T entry : ts) {
            datumWriter.write(entry, encoder);
        }

        encoder.flush();
        byteArrayOutputStream.close();
        outputMessage.getBody().write(byteArrayOutputStream.toByteArray());
    }
}
