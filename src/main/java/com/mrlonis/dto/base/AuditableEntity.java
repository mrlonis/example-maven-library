package com.mrlonis.dto.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mrlonis.jackson.ZonedDateTimeDeserializer;
import com.mrlonis.jackson.ZonedDateTimeSerializer;
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

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @Builder.Default
    private ZonedDateTime createdDate = ZonedDateTime.now(ZoneId.systemDefault());

    private String lastModifiedBy;

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @Builder.Default
    private ZonedDateTime lastModifiedDate = ZonedDateTime.now(ZoneId.systemDefault());
}
