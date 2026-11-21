package com.mrlonis.dto;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTimestampTests {
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("America/New_York");
    private static final ObjectMapper JSON = new ObjectMapper().setTimeZone(TIME_ZONE);
    private static final ObjectMapper XML = new XmlMapper().setTimeZone(TIME_ZONE);

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2026-01-01T01:02Z                         | 2026-01-01T01:02Z",
            "2026-01-01T01:02:03.123456789+05:30       | 2026-01-01T01:02:03.123456789+05:30",
            "2026-01-01T01:02:03-0530                  | 2026-01-01T01:02:03-05:30",
            "Thu, 1 Jan 2026 01:02:03 GMT              | 2026-01-01T01:02:03Z",
            "2026-07-01T01:02:03                       | 2026-07-01T01:02:03-04:00[America/New_York]",
            "2026-01-01 01:02:03.123                   | 2026-01-01T01:02:03.123-05:00[America/New_York]",
            "2026-01-01                                | 2026-01-01T00:00-05:00[America/New_York]"
    })
    void readsTextFormats(String input, String expected) throws Exception {
        String json = "{\"createdDate\":" + JSON.writeValueAsString(input) + "}";
        String xml = "<User><lastModifiedDate>" + input + "</lastModifiedDate></User>";
        assertEquals(ZonedDateTime.parse(expected), JSON.readValue(json, User.class).getCreatedDate());
        assertEquals(ZonedDateTime.parse(expected), XML.readValue(xml, User.class).getLastModifiedDate());
    }

    @Test
    void preservesRegionAndOffsetDuringDaylightSavingOverlap() throws Exception {
        ZonedDateTime later = ZonedDateTime.parse("2026-10-25T02:30:00.123456789+01:00[Europe/Paris]")
                .withLaterOffsetAtOverlap();
        User user = User.builder().createdDate(later.withEarlierOffsetAtOverlap()).lastModifiedDate(later).build();
        for (ObjectMapper mapper : new ObjectMapper[] {JSON, XML}) {
            User decoded = mapper.readValue(mapper.writeValueAsString(user), User.class);
            assertEquals(user.getCreatedDate(), decoded.getCreatedDate());
            assertEquals(user.getLastModifiedDate(), decoded.getLastModifiedDate());
        }
    }

    @Test
    void readsLegacyEpochTimestamps() throws Exception {
        Instant expected = Instant.ofEpochSecond(-1, 999999999);
        User json = JSON.readValue("{\"createdDate\":-0.000000001,\"lastModifiedDate\":2}", User.class);
        User xml = XML.readValue("<User><createdDate>-0.000000001</createdDate></User>", User.class);
        assertEquals(expected, json.getCreatedDate().toInstant());
        assertEquals(expected, xml.getCreatedDate().toInstant());
        assertEquals(Instant.ofEpochSecond(2), json.getLastModifiedDate().toInstant());

        User millis = JSON.readerFor(User.class)
                .without(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .readValue("{\"createdDate\":1234}");
        assertEquals(Instant.ofEpochMilli(1234), millis.getCreatedDate().toInstant());
    }

    @Test
    void rejectsInvalidCalendarDates() {
        assertThrows(InvalidFormatException.class,
                () -> JSON.readValue("{\"createdDate\":\"2026-02-30T12:00:00Z\"}", User.class));
    }
}
