package com.mrlonis.dto;

import com.mrlonis.test.BaseXmlTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTests extends BaseXmlTests<User> {
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";

    @Override
    public String getExpectedXml() {
        return "<User><firstName>" + FIRST_NAME + "</firstName><lastName>" + LAST_NAME + "</lastName></User>";
    }

    @Override
    public String getExpectedJson() {
        return "{\"firstName\":\"" + FIRST_NAME + "\",\"lastName\":\"" + LAST_NAME + "\"}";
    }

    @Override
    public User createObject() {
        return User.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build();
    }

    @Override
    public void assertObject(User object) {
        assertNotNull(object);
        assertEquals(FIRST_NAME, object.getFirstName());
        assertEquals(LAST_NAME, object.getLastName());
    }

    @Override
    public Class<User> getObjectClass() {
        return User.class;
    }
}
