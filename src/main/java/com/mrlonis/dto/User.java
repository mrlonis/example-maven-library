package com.mrlonis.dto;

import com.mrlonis.dto.base.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Jacksonized
public class User extends AuditableEntity {
    private String firstName;
    private String lastName;
}
