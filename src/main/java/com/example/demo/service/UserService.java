package com.example.demo.service;

import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.exception.UserException;
import com.example.demo.model.User;
import com.example.demo.exception.InvalidEmailException;
import com.example.demo.exception.InvalidPasswordException;
import com.example.demo.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    Dotenv env = Dotenv.configure().load();

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegisterDTO userRegisterDTO) throws UserException {
        validateUser(userRegisterDTO);
        User user = new User();
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setName(userRegisterDTO.getName());
        user.setCnp(userRegisterDTO.getCnp());
        user.setSeriesAndNumber(userRegisterDTO.getSeriesAndNumber());
        user.setAddressLine1(userRegisterDTO.getAddressLine1());
        user.setAddressLine2(userRegisterDTO.getAddressLine2());
        user.setPhoneNumber(userRegisterDTO.getPhoneNumber());
        user.setPostalCode(userRegisterDTO.getPostalCode());
        user.setLocalityId(userRegisterDTO.getLocalityId());
        user.setCountyId(userRegisterDTO.getCountyId());
        user.setCountry(userRegisterDTO.getCountry());
        user.setLinkCIPhoto(userRegisterDTO.getLinkCIPhoto());

        userRepository.save(user);
    }

    private void validateUser(UserRegisterDTO user) throws UserException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new InvalidEmailException("An account with this email address already exists. " +
                    "Please try logging in or use a different email address");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidPasswordException("Password must have at least " + MIN_PASSWORD_LENGTH + " characters");
        }
    }
    public String login(String email, String password) throws UserException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password,user.get().getPassword())) {
            Dotenv env = Dotenv.configure().load();
            String secret = env.get("SECRET_KEY_JWT");
            final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour
            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                    SignatureAlgorithm.HS256.getJcaName());
            return Jwts.builder()
                    .claim("email", user.get().getEmail())
                    .claim("password", user.get().getPassword())
                    .setSubject(user.get().getEmail())
                    .setIssuedAt(Date.from(Instant.now()))
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                    .signWith(hmacKey)
                    .compact();
        } else {
            throw new UserException("Please check your password and account name and try again");
        }
    }

    public Optional<User> getUserFromToken(String token) {
        Claims claims = validateAndDecodeToken(token);

        if (claims != null) {
            String userEmail = claims.getSubject(); // Assuming the email is stored as the subject of the token
            Optional<User> user = userRepository.findByEmail(userEmail);

            return user;
        }

        return null; // Invalid or expired token
    }

    private Claims validateAndDecodeToken(String token) {
        try {
            String secret = env.get("SECRET_KEY_JWT");
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            // Handle token validation errors
            return null;
        }
    }
}