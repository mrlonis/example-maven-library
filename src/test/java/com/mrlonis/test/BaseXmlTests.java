package com.mrlonis.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

public abstract class BaseXmlTests<T> extends BaseJsonTests<T> {
    public static final XmlMapper XML_MAPPER =
            (XmlMapper) new XmlMapper().registerModule(new JavaTimeModule()).setTimeZone(TEST_TIME_ZONE);
    public static final XmlMapper CUSTOM_XML_MAPPER = buildCustomMapper(XmlMapper.builder());

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

    @Test
    void testXmlSerializationWithCustomMapper() throws Exception {
        assertEquals(getExpectedXml(), CUSTOM_XML_MAPPER.writeValueAsString(createObject()));
    }

    @Test
    void testXmlDeserializationWithCustomMapper() throws Exception {
        T object = CUSTOM_XML_MAPPER.readValue(getExpectedXml(), getObjectClass());
        assertObject(object);
    }
}
