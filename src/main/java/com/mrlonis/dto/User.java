package com.mrlonis.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.mrlonis.dto.base.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Jacksonized
@JsonRootName("User")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"version", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate", "firstName", "lastName"
})
public class User extends AuditableEntity {
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;
}
