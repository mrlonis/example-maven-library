package com.mrlonis.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mrlonis.test.BaseXmlTests;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class UserTests extends BaseXmlTests<User> {
    private static final long VERSION = 2;
    private static final String CREATED_BY = "creator";
    private static final ZonedDateTime CREATED_DATE = ZonedDateTime.of(2026, 1, 1, 1, 1, 1, 1, ZoneOffset.UTC);
    private static final String LAST_MODIFIED_BY = "editor";
    private static final ZonedDateTime LAST_MODIFIED_DATE = ZonedDateTime.of(2026, 1, 1, 1, 1, 1, 1, ZoneOffset.UTC);
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";

    @Override
    public String getExpectedXml() {
        return "<User><version>" + VERSION + "</version><createdBy>" + CREATED_BY
                + "</createdBy><createdDate>" + CREATED_DATE + "</createdDate>"
                + "<lastModifiedBy>" + LAST_MODIFIED_BY
                + "</lastModifiedBy><lastModifiedDate>" + LAST_MODIFIED_DATE + "</lastModifiedDate>"
                + "<firstName>" + FIRST_NAME + "</firstName><lastName>" + LAST_NAME + "</lastName></User>";
    }

    @Override
    public String getExpectedJson() {
        return "{\"version\":" + VERSION + ",\"createdBy\":\"" + CREATED_BY + "\",\"createdDate\":\"" + CREATED_DATE
                + "\","
                + "\"lastModifiedBy\":\"" + LAST_MODIFIED_BY + "\",\"lastModifiedDate\":\"" + LAST_MODIFIED_DATE + "\","
                + "\"firstName\":\"" + FIRST_NAME + "\",\"lastName\":\"" + LAST_NAME + "\"}";
    }

    @Override
    public User createObject() {
        return User.builder()
                .version(VERSION)
                .createdBy(CREATED_BY)
                .createdDate(CREATED_DATE)
                .lastModifiedBy(LAST_MODIFIED_BY)
                .lastModifiedDate(LAST_MODIFIED_DATE)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
    }

    @Override
    public void assertObject(User object) {
        assertNotNull(object);
        assertEquals(VERSION, object.getVersion());
        assertEquals(CREATED_BY, object.getCreatedBy());
        assertEquals(CREATED_DATE, object.getCreatedDate());
        assertEquals(LAST_MODIFIED_BY, object.getLastModifiedBy());
        assertEquals(LAST_MODIFIED_DATE, object.getLastModifiedDate());
        assertEquals(FIRST_NAME, object.getFirstName());
        assertEquals(LAST_NAME, object.getLastName());
    }

    @Override
    public Class<User> getObjectClass() {
        return User.class;
    }

    @Test
    void omitsNullPropertiesAndKeepsNonNullValuesInOrder() throws Exception {
        User user = User.builder()
                .createdDate(null)
                .lastModifiedDate(null)
                .lastModifiedBy(LAST_MODIFIED_BY)
                .firstName("")
                .build();
        assertEquals(
                "{\"version\":1,\"lastModifiedBy\":\"editor\",\"firstName\":\"\"}",
                CUSTOM_OBJECT_MAPPER.writeValueAsString(user));
        assertEquals(
                "<User><version>1</version><lastModifiedBy>editor</lastModifiedBy><firstName></firstName></User>",
                CUSTOM_XML_MAPPER.writeValueAsString(user));
    }

    @Test
    void ignoresUnknownPropertiesAndKeepsAuditDefaults() throws Exception {
        String json = "{\"future\":{\"value\":1},\"firstName\":\"John\"}";
        String xml = "<User><future><value>1</value></future><firstName>John</firstName></User>";
        for (User user : new User[] {
            CUSTOM_OBJECT_MAPPER.readValue(json, User.class), CUSTOM_XML_MAPPER.readValue(xml, User.class)
        }) {
            assertEquals(FIRST_NAME, user.getFirstName());
            assertEquals(1, user.getVersion());
            assertNotNull(user.getCreatedDate());
            assertNotNull(user.getLastModifiedDate());
        }
    }

    @Test
    void preservesExplicitNullsForAuditFields() throws Exception {
        String json = "{\"version\":null,\"createdDate\":null,\"lastModifiedDate\":null}";
        String xml = "<User xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><version"
                + " xsi:nil=\"true\"/><createdDate xsi:nil=\"true\"/><lastModifiedDate xsi:nil=\"true\"/></User>";
        for (User user : new User[] {
            CUSTOM_OBJECT_MAPPER.readValue(json, User.class), CUSTOM_XML_MAPPER.readValue(xml, User.class)
        }) {
            assertEquals(0, user.getVersion());
            assertNull(user.getCreatedDate());
            assertNull(user.getLastModifiedDate());
        }
    }

    @ParameterizedTest
    @CsvSource(
            delimiter = '|',
            value = {
                "2026-01-01T01:02Z                         | 2026-01-01T01:02Z",
                "2026-01-01T01:02:03.123456789+05:30       | 2026-01-01T01:02:03.123456789+05:30",
                "2026-01-01T01:02:03-0530                  | 2026-01-01T01:02:03-05:30",
                "Thu, 1 Jan 2026 01:02:03 GMT              | 2026-01-01T01:02:03Z",
                "2026-07-01T01:02:03                       | 2026-07-01T01:02:03-04:00[America/New_York]",
                "2026-01-01 01:02:03.123                   | 2026-01-01T01:02:03.123-05:00[America/New_York]",
                "2026-01-01                                | 2026-01-01T00:00-05:00[America/New_York]"
            })
    void readsTextFormats(String input, String expected) throws Exception {
        String json = "{\"createdDate\":" + OBJECT_MAPPER.writeValueAsString(input) + "}";
        String xml = "<User><lastModifiedDate>" + input + "</lastModifiedDate></User>";
        assertEquals(
                ZonedDateTime.parse(expected),
                OBJECT_MAPPER.readValue(json, User.class).getCreatedDate());
        assertEquals(
                ZonedDateTime.parse(expected),
                XML_MAPPER.readValue(xml, User.class).getLastModifiedDate());
    }

    @Test
    void preservesRegionAndOffsetDuringDaylightSavingOverlap() throws Exception {
        ZonedDateTime later = ZonedDateTime.parse("2026-10-25T02:30:00.123456789+01:00[Europe/Paris]")
                .withLaterOffsetAtOverlap();
        User user = User.builder()
                .createdDate(later.withEarlierOffsetAtOverlap())
                .lastModifiedDate(later)
                .build();
        for (ObjectMapper mapper : new ObjectMapper[] {OBJECT_MAPPER, XML_MAPPER}) {
            User decoded = mapper.readValue(mapper.writeValueAsString(user), User.class);
            assertEquals(user.getCreatedDate(), decoded.getCreatedDate());
            assertEquals(user.getLastModifiedDate(), decoded.getLastModifiedDate());
        }
    }

    @Test
    void readsLegacyEpochTimestamps() throws Exception {
        Instant expected = Instant.ofEpochSecond(-1, 999999999);
        User json = OBJECT_MAPPER.readValue("{\"createdDate\":-0.000000001,\"lastModifiedDate\":2}", User.class);
        User xml = XML_MAPPER.readValue("<User><createdDate>-0.000000001</createdDate></User>", User.class);
        assertEquals(expected, json.getCreatedDate().toInstant());
        assertEquals(expected, xml.getCreatedDate().toInstant());
        assertEquals(Instant.ofEpochSecond(2), json.getLastModifiedDate().toInstant());

        User millis = OBJECT_MAPPER
                .readerFor(User.class)
                .without(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .readValue("{\"createdDate\":1234}");
        assertEquals(Instant.ofEpochMilli(1234), millis.getCreatedDate().toInstant());
    }

    @Test
    void rejectsInvalidCalendarDates() {
        assertThrows(
                InvalidFormatException.class,
                () -> OBJECT_MAPPER.readValue("{\"createdDate\":\"2026-02-30T12:00:00Z\"}", User.class));
    }
}
