package com.example.mytransformers;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.transforms.Transformation;
import org.apache.kafka.connect.transforms.util.SimpleConfig;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.connect.transforms.util.Requirements.requireMap;
import static org.apache.kafka.connect.transforms.util.Requirements.requireStruct;

@Slf4j
public abstract class MyTransformers<R extends ConnectRecord<R>> implements Transformation<R> {

    public static final ConfigDef CONFIG_DEF = new ConfigDef()
            .define(ConfigName.FIELD_NAME,
                    ConfigDef.Type.STRING,
                    ConfigDef.NO_DEFAULT_VALUE,
                    ConfigDef.Importance.HIGH,
                    "the field of appendValue.")
            .define(ConfigName.APPEND_VALUE,
                    ConfigDef.Type.STRING,
                    ConfigDef.NO_DEFAULT_VALUE,
                    ConfigDef.Importance.HIGH,
                    "the append value.");
    private static final String PURPOSE = "append value";
    private String fieldName = null;
    private String appendValue = null;

    @Override
    public R apply(R record) {
        if (operatingSchema(record) == null) {
            return applySchemaless(record);
        } else {
            return applyWithSchema(record);
        }
    }

    private R applySchemaless(R record) {
        final Map<String, Object> value = requireMap(operatingValue(record), PURPOSE);
        final HashMap<String, Object> updatedValue = new HashMap<>(value);

        updatedValue.put(fieldName, value.getOrDefault(fieldName, Strings.EMPTY) + appendValue);

        return newRecord(record, updatedValue);
    }

    private R applyWithSchema(R record) {
        final Struct value = requireStruct(operatingValue(record), PURPOSE);
        final Struct updatedValue = new Struct(value.schema());
        for (Field field : value.schema().fields()) {
            final Object origFieldValue = value.get(field);
            updatedValue.put(field, fieldName.endsWith(field.name()) ? origFieldValue + appendValue : origFieldValue);
        }
        return newRecord(record, updatedValue);
    }

    @Override
    public ConfigDef config() {
        return CONFIG_DEF;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {
        final SimpleConfig config = new SimpleConfig(CONFIG_DEF, map);
        fieldName = config.getString(ConfigName.FIELD_NAME);
        appendValue = config.getString(ConfigName.APPEND_VALUE);
        log.info("filedName is {},appendValue is {}", fieldName, appendValue);
    }

    protected abstract Schema operatingSchema(R record);

    protected abstract Object operatingValue(R record);

    protected abstract R newRecord(R base, Object value);

    private interface ConfigName {
        String FIELD_NAME = "fieldName";
        String APPEND_VALUE = "appendValue";
    }

    public static final class Key<R extends ConnectRecord<R>> extends MyTransformers<R> {
        @Override
        protected Schema operatingSchema(R record) {
            return record.keySchema();
        }

        @Override
        protected Object operatingValue(R record) {
            return record.key();
        }

        @Override
        protected R newRecord(R record, Object updatedValue) {
            return record.newRecord(record.topic(),
                                    record.kafkaPartition(),
                                    record.keySchema(),
                                    updatedValue,
                                    record.valueSchema(),
                                    record.value(),
                                    record.timestamp());
        }
    }

    public static final class Value<R extends ConnectRecord<R>> extends MyTransformers<R> {
        @Override
        protected Schema operatingSchema(R record) {
            return record.valueSchema();
        }

        @Override
        protected Object operatingValue(R record) {
            return record.value();
        }

        @Override
        protected R newRecord(R record, Object updatedValue) {
            return record.newRecord(record.topic(),
                                    record.kafkaPartition(),
                                    record.keySchema(),
                                    record.key(),
                                    record.valueSchema(),
                                    updatedValue,
                                    record.timestamp());
        }
    }
}
