package com.shouldibunk.backend.repository;

import com.shouldibunk.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA is smart! It automatically creates a query
    // to find a User by their email address just from the method name.
    Optional<User> findByEmail(String email);
}
