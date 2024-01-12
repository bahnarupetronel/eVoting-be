package com.example.demo.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.amazonaws.services.qldb.model.ResourceAlreadyExistsException;
import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.exception.UserException;
import com.example.demo.model.User;
import com.example.demo.exception.InvalidEmailException;
import com.example.demo.exception.InvalidPasswordException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service@RequiredArgsConstructor
public class UserService {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

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
            throw new ResourceAlreadyExistsException("An account with this email address already exists. " +
                    "Please try logging in or use a different email address");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidPasswordException("Password must have at least " + MIN_PASSWORD_LENGTH + " characters");
        }
    }

    public User getUser(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromCookies(request);
        User user = null;
        if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
            String email = jwtUtils.getEmailFromJwtToken(jwt);
            user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(new String("Userul nu exista")));;
        }
        return user;
    }
}