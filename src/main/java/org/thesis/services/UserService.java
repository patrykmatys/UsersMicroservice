package org.thesis.services;

import org.thesis.models.UserRegistrationRequest;

public interface UserService {
    String registerNewUser(UserRegistrationRequest user);
    String authenticateUser(UserRegistrationRequest user);
    Boolean validateToken(String token);
}
