package com.mrlonis.dto.base;

import lombok.Builder;
import lombok.Data;
import lombok.SuperBuilder;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@SuperBuilder
public abstract class AuditableEntity {
    @Builder.Default
    private long version = 1;

    private String createdBy;

    @Builder.Default
    private ZonedDateTime createdDate = ZonedDateTime.now(ZoneId.systemDefault());

    private String lastModifiedBy;

    @Builder.Default
    private ZonedDateTime lastModifiedDate = ZonedDateTime.now(ZoneId.systemDefault());
}
