package com.mrlonis.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mrlonis.dto.base.AuditableEntityTests;
import com.mrlonis.enums.AddressType;
import com.mrlonis.test.BaseXmlTests;

public class AddressTests extends BaseXmlTests<Address> {
    private static final String STREET1 = "123 Main St";
    private static final String STREET2 = "Apt 4B";
    private static final String CITY = "Anytown";
    private static final String STATE = "CA";
    private static final String ZIP_CODE = "12345";
    private static final String COUNTRY = "USA";
    private static final AddressType ADDRESS_TYPE = AddressType.PHYSICAL;

    @Override
    public String getExpectedXml() {
        return "<Address><version>" + AuditableEntityTests.VERSION + "</version><createdBy>"
                + AuditableEntityTests.CREATED_BY + "</createdBy><createdDate>"
                + AuditableEntityTests.CREATED_DATE
                + "</createdDate><lastModifiedBy>" + AuditableEntityTests.LAST_MODIFIED_BY
                + "</lastModifiedBy><lastModifiedDate>"
                + AuditableEntityTests.LAST_MODIFIED_DATE
                + "</lastModifiedDate><street1>" + STREET1 + "</street1><street2>" + STREET2 + "</street2><city>" + CITY
                + "</city><state>" + STATE + "</state><zipCode>" + ZIP_CODE + "</zipCode><country>" + COUNTRY
                + "</country><addressType>" + ADDRESS_TYPE + "</addressType></Address>";
    }

    @Override
    public String getExpectedJson() {
        return "{\"version\":" + AuditableEntityTests.VERSION + ",\"createdBy\":\"" + AuditableEntityTests.CREATED_BY
                + "\",\"createdDate\":\"" + AuditableEntityTests.CREATED_DATE
                + "\",\"lastModifiedBy\":\"" + AuditableEntityTests.LAST_MODIFIED_BY + "\",\"lastModifiedDate\":\""
                + AuditableEntityTests.LAST_MODIFIED_DATE
                + "\",\"street1\":\"" + STREET1 + "\",\"street2\":\"" + STREET2 + "\",\"city\":\"" + CITY
                + "\",\"state\":\"" + STATE + "\",\"zipCode\":\"" + ZIP_CODE + "\",\"country\":\"" + COUNTRY
                + "\",\"addressType\":\"" + ADDRESS_TYPE + "\"}";
    }

    @Override
    public Address createObject() {
        return Address.builder()
                .version(AuditableEntityTests.VERSION)
                .createdBy(AuditableEntityTests.CREATED_BY)
                .createdDate(AuditableEntityTests.CREATED_DATE)
                .lastModifiedBy(AuditableEntityTests.LAST_MODIFIED_BY)
                .lastModifiedDate(AuditableEntityTests.LAST_MODIFIED_DATE)
                .street1(STREET1)
                .street2(STREET2)
                .city(CITY)
                .state(STATE)
                .zipCode(ZIP_CODE)
                .country(COUNTRY)
                .addressType(ADDRESS_TYPE)
                .build();
    }

    @Override
    public void assertObject(Address object) {
        assertNotNull(object);
        assertEquals(AuditableEntityTests.VERSION, object.getVersion());
        assertEquals(AuditableEntityTests.CREATED_BY, object.getCreatedBy());
        assertEquals(AuditableEntityTests.CREATED_DATE, object.getCreatedDate());
        assertEquals(AuditableEntityTests.LAST_MODIFIED_BY, object.getLastModifiedBy());
        assertEquals(AuditableEntityTests.LAST_MODIFIED_DATE, object.getLastModifiedDate());
        assertEquals(STREET1, object.getStreet1());
        assertEquals(STREET2, object.getStreet2());
        assertEquals(CITY, object.getCity());
        assertEquals(STATE, object.getState());
        assertEquals(ZIP_CODE, object.getZipCode());
        assertEquals(COUNTRY, object.getCountry());
        assertEquals(ADDRESS_TYPE, object.getAddressType());
    }

    @Override
    public Class<Address> getObjectClass() {
        return Address.class;
    }
}
