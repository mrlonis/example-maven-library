package com.mrlonis;

import static com.mrlonis.test.BaseJsonTests.CUSTOM_OBJECT_MAPPER;
import static com.mrlonis.test.BaseXmlTests.CUSTOM_XML_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ExampleTest {
    @Test
    void omitsNullName() throws Exception {
        ExamplePojo pojo = ExamplePojo.builder().build();
        assertEquals("{}", CUSTOM_OBJECT_MAPPER.writeValueAsString(pojo));
        assertEquals("<ExamplePojo/>", CUSTOM_XML_MAPPER.writeValueAsString(pojo));
    }

    @Test
    void ignoresUnknownPropertiesAndRetainsName() throws Exception {
        String json = "{\"future\":{\"value\":1},\"name\":\"example\"}";
        String xml = "<ExamplePojo><future><value>1</value></future><name>example</name></ExamplePojo>";
        ExamplePojo expected = ExamplePojo.builder().name("example").build();
        assertEquals(expected, CUSTOM_OBJECT_MAPPER.readValue(json, ExamplePojo.class));
        assertEquals(expected, CUSTOM_XML_MAPPER.readValue(xml, ExamplePojo.class));
        assertEquals("{\"name\":\"example\"}", CUSTOM_OBJECT_MAPPER.writeValueAsString(expected));
        assertEquals("<ExamplePojo><name>example</name></ExamplePojo>", CUSTOM_XML_MAPPER.writeValueAsString(expected));
    }
}
