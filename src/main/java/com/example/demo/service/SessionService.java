package com.example.demo.service;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class SessionService {

    public boolean validateToken(String token) {
        Dotenv env = Dotenv.configure().load();

        String secret = env.get("SECRET_KEY_JWT");

        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
