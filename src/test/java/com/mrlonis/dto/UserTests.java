package com.mrlonis.dto;

import com.mrlonis.test.BaseXmlTests;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTests extends BaseXmlTests<User> {
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final long VERSION = 2;
    private static final String CREATED_BY = "creator";
    private static final ZonedDateTime CREATED_DATE = ZonedDateTime.of(2026, 1, 1, 1, 1, 1, 1, ZoneOffset.UTC);
    private static final String LAST_MODIFIED_BY = "editor";
    private static final ZonedDateTime MODIFIED_DATE = ZonedDateTime.of(2026, 1, 1, 1, 1, 1, 1, ZoneOffset.UTC);

    @Override
    public String getExpectedXml() {
        return "<User><version>" + VERSION + "</version><createdBy>" + CREATED_BY
                + "</createdBy><createdDate>" + CREATED_DATE + "</createdDate>"
                + "<lastModifiedBy>" + LAST_MODIFIED_BY
                + "</lastModifiedBy><lastModifiedDate>" + MODIFIED_DATE + "</lastModifiedDate>"
                + "<firstName>" + FIRST_NAME + "</firstName><lastName>" + LAST_NAME + "</lastName></User>";
    }

    @Override
    public String getExpectedJson() {
        return "{\"version\":" + VERSION + ",\"createdBy\":\"" + CREATED_BY + "\",\"createdDate\":\"" + CREATED_DATE + "\","
                + "\"lastModifiedBy\":\"" + LAST_MODIFIED_BY + "\",\"lastModifiedDate\":\"" + MODIFIED_DATE + "\","
                + "\"firstName\":\"" + FIRST_NAME + "\",\"lastName\":\"" + LAST_NAME + "\"}";
    }

    @Override
    public User createObject() {
        return User.builder()
                .version(VERSION)
                .createdBy(CREATED_BY)
                .createdDate(CREATED_DATE)
                .lastModifiedBy(LAST_MODIFIED_BY)
                .lastModifiedDate(MODIFIED_DATE)
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
        assertEquals(MODIFIED_DATE, object.getLastModifiedDate());
        assertEquals(FIRST_NAME, object.getFirstName());
        assertEquals(LAST_NAME, object.getLastName());
    }

    @Override
    public Class<User> getObjectClass() {
        return User.class;
    }
}
