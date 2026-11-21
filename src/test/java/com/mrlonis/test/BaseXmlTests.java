package com.mrlonis.test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BaseXmlTests<T> extends BaseJsonTests<T> {
    public static final XmlMapper XML_MAPPER =
            (XmlMapper) new XmlMapper().registerModule(new JavaTimeModule()).setTimeZone(TEST_TIME_ZONE);

    public abstract String getExpectedXml();

    @Test
    void testXmlSerialization() throws Exception {
        T object = createObject();
        String json = XML_MAPPER.writeValueAsString(object);
        String expectedJson = getExpectedXml();
        assertEquals(expectedJson, json);
    }

    @Test
    void testXmlDeserialization() throws Exception {
        String json = getExpectedXml();
        T object = XML_MAPPER.readValue(json, getObjectClass());
        assertObject(object);
    }
}
