package org.thesis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thesis.models.UserRegistrationRequest;
import org.thesis.services.UserService;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/v1/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {
        userService.registerNewUser(registrationRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/api/v1/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestBody UserRegistrationRequest registrationRequest) {
        if (userService.authenticateUser(registrationRequest)) {
            return ResponseEntity.ok("User authenticated");
        } else {
            return ResponseEntity.status(403).body("User not authenticated");
        }
    }
}
