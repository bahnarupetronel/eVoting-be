package com.example.demo.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.amazonaws.services.glue.model.EntityNotFoundException;
import com.amazonaws.services.qldb.model.ResourceAlreadyExistsException;
import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.exception.UserException;
import com.example.demo.model.ChangePasswordToken;
import com.example.demo.model.ConfirmEmailToken;
import com.example.demo.model.User;
import com.example.demo.exception.InvalidPasswordException;
import com.example.demo.payload.ChangePasswordRequest;
import com.example.demo.payload.ForgotPasswordRequest;
import com.example.demo.repository.ChangePasswordTokenRepository;
import com.example.demo.repository.ConfirmEmailTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service@RequiredArgsConstructor
public class UserService {
    private static final int MIN_PASSWORD_LENGTH = 8;
    private final EmailService emailService;
    private final ChangePasswordTokenRepository changePasswordTokenRepository;
    private final ConfirmEmailTokenRepository confirmEmailTokenRepository;
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

    private void validateUser(UserRegisterDTO user) {
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

    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest){
        User user = userRepository.findByEmail(forgotPasswordRequest.getEmail()).orElseThrow(() -> new EntityNotFoundException("Eroare."));
        ChangePasswordToken changePasswordToken = new ChangePasswordToken(user);
        ChangePasswordToken token = changePasswordTokenRepository.findByUserId(user.getId());
        if(token!= null){
            token.setChangePasswordToken(changePasswordToken.getChangePasswordToken());
            token.setCreatedDate(changePasswordToken.getCreatedDate());
            changePasswordTokenRepository.save(token);
        }
        else {
            changePasswordTokenRepository.save(changePasswordToken);
        }

        sendForgotPasswordEmail(user.getEmail(), changePasswordToken.getChangePasswordToken());
    }

    public void changePassword(String token, ChangePasswordRequest password){
        ChangePasswordToken changePasswordToken = changePasswordTokenRepository.findByChangePasswordToken(token);
            User user = changePasswordToken.getUser();
            user.setPassword(passwordEncoder.encode(password.getPassword()));
            user.setChangePasswordToken(null);
            userRepository.save(user);
            changePasswordTokenRepository.delete(changePasswordToken);
            sendChangePasswordConfirmation(user.getEmail());
    }

    public void confirmAccount(HttpServletRequest request){
        User user  = getUser(request);
        ConfirmEmailToken confirmEmailToken = new ConfirmEmailToken(user);
        ConfirmEmailToken token = confirmEmailTokenRepository.findByUserId(user.getId());
        if(token!= null){
            token.setConfirmEmailToken(confirmEmailToken.getConfirmEmailToken());
            token.setCreatedDate(confirmEmailToken.getCreatedDate());
            confirmEmailTokenRepository.save(token);
        }
        else {
            confirmEmailTokenRepository.save(confirmEmailToken);
        }

        sendConfirmAccount(user.getEmail(), token.getConfirmEmailToken());
    }

    private void sendForgotPasswordEmail(String email, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Schimba parola!");
        mailMessage.setText("Pentru a schimba parola, acceseaza link-ul : "
                +"http://127.0.0.1:3000/change-password?token="+ token);
        emailService.sendEmail(mailMessage);
    }

    private void sendChangePasswordConfirmation(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Confirmare schimbare parola!");
        mailMessage.setText("Parola a fost schimbata. Daca nu ati schimbat parola, accesati link-ul urmator pentru a va schimba parola in siguranta : "
                +"http://127.0.0.1:3000/forgot-password");
        emailService.sendEmail(mailMessage);
    }

    private void sendConfirmAccount(String email, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Confirma adresa de email a contului!");
        mailMessage.setText("Pentru a confirma adresa de email, acceseaza link-ul urmator: "
                +"http://127.0.0.1:3000/confirm-account?token="+ token);
        emailService.sendEmail(mailMessage);
    }
}