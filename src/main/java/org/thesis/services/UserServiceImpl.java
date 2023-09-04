package org.thesis.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thesis.models.User;
import org.thesis.models.UserRegistrationRequest;
import org.thesis.repositories.UserRepository;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long expirationMillis = 3600000; // 1 hour

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String registerNewUser(UserRegistrationRequest user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(encodedPassword);

        userRepository.save(newUser);

        return generateToken(user.getEmail());
    }

    @Override
    public String authenticateUser(UserRegistrationRequest user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is not registered.");
        }

        if(passwordEncoder.matches(
                user.getPassword(),
                userRepository.findByEmail(user.getEmail()).get().getPassword()
        )) {
            return generateToken(user.getEmail());
        } else {
            return null;
        }
    }

    private static String generateToken(String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    @Override
    public Boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
