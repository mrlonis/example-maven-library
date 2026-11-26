package com.mrlonis.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.mrlonis.dto.base.AuditableEntity;
import com.mrlonis.enums.AddressType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Jacksonized
@JsonRootName("Address")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "version",
    "createdBy",
    "createdDate",
    "lastModifiedBy",
    "lastModifiedDate",
    "street1",
    "street2",
    "city",
    "state",
    "zipCode",
    "country",
    "addressType"
})
public class Address extends AuditableEntity {
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private AddressType addressType;
}
