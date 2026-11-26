package com.mrlonis.dto.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.mrlonis.test.BaseXmlTests;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

public class AuditableEntityTests extends BaseXmlTests<AuditableEntityTests.TestAuditableEntity> {
    private static final long VERSION = 2;
    private static final String CREATED_BY = "creator";
    private static final ZonedDateTime CREATED_DATE = ZonedDateTime.of(2026, 1, 1, 1, 1, 1, 1, ZoneOffset.UTC);
    private static final String LAST_MODIFIED_BY = "editor";
    private static final ZonedDateTime LAST_MODIFIED_DATE = ZonedDateTime.of(2026, 1, 2, 2, 2, 2, 2, ZoneOffset.UTC);

    @SuperBuilder
    @Jacksonized
    @JsonRootName("TestAuditableEntity")
    static class TestAuditableEntity extends AuditableEntity {}

    @Override
    public String getExpectedXml() {
        return "<TestAuditableEntity><version>" + VERSION + "</version><createdBy>" + CREATED_BY
                + "</createdBy><createdDate>" + CREATED_DATE + "</createdDate><lastModifiedBy>" + LAST_MODIFIED_BY
                + "</lastModifiedBy><lastModifiedDate>" + LAST_MODIFIED_DATE
                + "</lastModifiedDate></TestAuditableEntity>";
    }

    @Override
    public String getExpectedJson() {
        return "{\"version\":" + VERSION + ",\"createdBy\":\"" + CREATED_BY + "\",\"createdDate\":\"" + CREATED_DATE
                + "\",\"lastModifiedBy\":\"" + LAST_MODIFIED_BY + "\",\"lastModifiedDate\":\"" + LAST_MODIFIED_DATE
                + "\"}";
    }

    @Override
    public TestAuditableEntity createObject() {
        return TestAuditableEntity.builder()
                .version(VERSION)
                .createdBy(CREATED_BY)
                .createdDate(CREATED_DATE)
                .lastModifiedBy(LAST_MODIFIED_BY)
                .lastModifiedDate(LAST_MODIFIED_DATE)
                .build();
    }

    @Override
    public void assertObject(TestAuditableEntity object) {
        assertNotNull(object);
        assertEquals(VERSION, object.getVersion());
        assertEquals(CREATED_BY, object.getCreatedBy());
        assertEquals(CREATED_DATE, object.getCreatedDate());
        assertEquals(LAST_MODIFIED_BY, object.getLastModifiedBy());
        assertEquals(LAST_MODIFIED_DATE, object.getLastModifiedDate());
    }

    @Override
    public Class<TestAuditableEntity> getObjectClass() {
        return TestAuditableEntity.class;
    }
}
