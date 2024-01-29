package com.example.demo.repository;

import com.example.demo.model.ChangePasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChangePasswordTokenRepository extends JpaRepository<ChangePasswordToken, Long> {
    ChangePasswordToken findByChangePasswordToken(String changePasswordToken);

    ChangePasswordToken findByUserId(Long userId);
}
