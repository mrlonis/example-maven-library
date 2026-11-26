package com.mrlonis.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("street1")
    private String street1;

    @JsonProperty("street2")
    private String street2;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("addressType")
    private AddressType addressType;
}
