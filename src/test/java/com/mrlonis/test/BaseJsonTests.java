package com.mrlonis.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.cfg.MapperBuilder;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BaseJsonTests<T> {
    public static final TimeZone TEST_TIME_ZONE = TimeZone.getTimeZone("America/New_York");
    public static final ObjectMapper OBJECT_MAPPER = buildTestMapper(JsonMapper.builder());

    // The POJO annotations must preserve the wire format even with these mapper defaults.
    static <M extends ObjectMapper> M buildTestMapper(MapperBuilder<M, ?> builder) {
        return builder.addModule(new JavaTimeModule())
                .defaultTimeZone(TEST_TIME_ZONE)
                .propertyNamingStrategy(PropertyNamingStrategies.UPPER_SNAKE_CASE)
                .defaultPropertyInclusion(JsonInclude.Value.construct(JsonInclude.Include.ALWAYS, JsonInclude.Include.ALWAYS))
                .defaultSetterInfo(JsonSetter.Value.forValueNulls(Nulls.SKIP))
                .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
                .build();
    }

    public abstract String getExpectedJson();

    public abstract T createObject();

    public abstract void assertObject(T object);

    public abstract Class<T> getObjectClass();

    @Test
    void testJsonSerialization() throws Exception {
        T object = createObject();
        String json = OBJECT_MAPPER.writeValueAsString(object);
        String expectedJson = getExpectedJson();
        assertEquals(expectedJson, json);
    }

    @Test
    void testJsonDeserialization() throws Exception {
        String json = getExpectedJson();
        T object = OBJECT_MAPPER.readValue(json, getObjectClass());
        assertObject(object);
    }
}
