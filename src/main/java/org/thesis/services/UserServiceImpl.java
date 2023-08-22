package org.thesis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thesis.models.User;
import org.thesis.models.UserRegistrationRequest;
import org.thesis.repositories.UserRepository;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerNewUser(UserRegistrationRequest user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(encodedPassword);

        userRepository.save(newUser);
    }

    @Override
    public boolean authenticateUser(UserRegistrationRequest user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is not registered.");
        }
        return passwordEncoder.matches(
                user.getPassword(),
                userRepository.findByEmail(user.getEmail()).get().getPassword()
        );
    }
}
