package org.thesis.repositories;

import org.springframework.data.repository.CrudRepository;
import org.thesis.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
