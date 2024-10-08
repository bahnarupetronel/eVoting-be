package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByCnp(String cnp);

    Optional<User> findByCnp(String cnp);

    Optional<User> findByEmail(String email);
}