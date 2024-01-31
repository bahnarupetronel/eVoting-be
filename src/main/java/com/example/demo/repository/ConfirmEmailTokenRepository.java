package com.example.demo.repository;

import com.example.demo.model.ConfirmEmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ConfirmEmailTokenRepository extends JpaRepository<ConfirmEmailToken, Long> {
    ConfirmEmailToken findByConfirmEmailToken(String confirmEmailToken);

    Optional<ConfirmEmailToken> findByUserId(Long userId);
}
