package com.mrlonis.dto.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"version", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"})
public abstract class AuditableEntity {
    @JsonProperty("version")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @Builder.Default
    private long version = 1;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("createdDate")
    @JsonSetter(nulls = Nulls.SET)
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @Builder.Default
    private ZonedDateTime createdDate = ZonedDateTime.now(ZoneId.systemDefault());

    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy;

    @JsonProperty("lastModifiedDate")
    @JsonSetter(nulls = Nulls.SET)
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @Builder.Default
    private ZonedDateTime lastModifiedDate = ZonedDateTime.now(ZoneId.systemDefault());
}
