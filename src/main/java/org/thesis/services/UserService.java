package org.thesis.services;

import org.thesis.models.UserRegistrationRequest;

public interface UserService {
    void registerNewUser(UserRegistrationRequest user);
    boolean authenticateUser(UserRegistrationRequest user);
}
