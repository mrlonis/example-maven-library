package com.mrlonis.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BaseJsonTests<T> {
    public static final TimeZone TEST_TIME_ZONE = TimeZone.getTimeZone("America/New_York");
    public static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper().registerModule(new JavaTimeModule()).setTimeZone(TEST_TIME_ZONE);

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
