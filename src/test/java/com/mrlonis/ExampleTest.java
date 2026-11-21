package com.mrlonis;

import org.junit.jupiter.api.Test;

import static com.mrlonis.test.BaseJsonTests.OBJECT_MAPPER;
import static com.mrlonis.test.BaseXmlTests.XML_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleTest {
    @Test
    void omitsNullName() throws Exception {
        ExamplePojo pojo = ExamplePojo.builder().build();
        assertEquals("{}", OBJECT_MAPPER.writeValueAsString(pojo));
        assertEquals("<ExamplePojo/>", XML_MAPPER.writeValueAsString(pojo));
    }

    @Test
    void ignoresUnknownPropertiesAndRetainsName() throws Exception {
        String json = "{\"future\":{\"value\":1},\"name\":\"example\"}";
        String xml = "<ExamplePojo><future><value>1</value></future><name>example</name></ExamplePojo>";
        ExamplePojo expected = ExamplePojo.builder().name("example").build();
        assertEquals(expected, OBJECT_MAPPER.readValue(json, ExamplePojo.class));
        assertEquals(expected, XML_MAPPER.readValue(xml, ExamplePojo.class));
        assertEquals("{\"name\":\"example\"}", OBJECT_MAPPER.writeValueAsString(expected));
        assertEquals("<ExamplePojo><name>example</name></ExamplePojo>", XML_MAPPER.writeValueAsString(expected));
    }
}
