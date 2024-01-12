package com.example.demo.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ExpirationDate {

    public static LocalDateTime createExpirationDate() {
        Dotenv env = Dotenv.configure().load();
        int jwtExpirationDate = Integer.parseInt(env.get("JWT_ACCES_EXPIRATION"));
        long futureTimeInSeconds = Instant.now().getEpochSecond() + jwtExpirationDate;

        LocalDateTime futureDate = LocalDateTime.ofInstant(Instant.ofEpochSecond(futureTimeInSeconds), ZoneId.of("GMT"));

        return futureDate;
    }
}
