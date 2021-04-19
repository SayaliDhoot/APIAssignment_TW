package com.thoughtworks.apiAssignment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationRequest {

    private String email;
    private String password;
}