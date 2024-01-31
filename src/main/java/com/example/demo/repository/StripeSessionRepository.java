package com.example.demo.repository;

import com.example.demo.model.StripeSession;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StripeSessionRepository extends JpaRepository<StripeSession, Long> {
    StripeSession findBySessionToken(String sessionToken);
    StripeSession findByUser(User user);
}
