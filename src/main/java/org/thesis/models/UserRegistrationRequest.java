package org.thesis.models;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String email;
    private String password;
}
