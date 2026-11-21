package com.mrlonis.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class User {
    private String firstName;
    private String lastName;
}
