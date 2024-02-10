package com.example.demo.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.amazonaws.services.glue.model.EntityNotFoundException;
import com.amazonaws.services.qldb.model.ResourceAlreadyExistsException;
import com.example.demo.model.Locality;
import com.example.demo.payload.UserEditDTO;
import com.example.demo.payload.UserRegisterDTO;
import com.example.demo.exception.UserException;
import com.example.demo.model.ChangePasswordToken;
import com.example.demo.model.ConfirmEmailToken;
import com.example.demo.model.User;
import com.example.demo.exception.InvalidPasswordException;
import com.example.demo.payload.ChangePasswordRequest;
import com.example.demo.payload.EmailRequest;
import com.example.demo.repository.ChangePasswordTokenRepository;
import com.example.demo.repository.ConfirmEmailTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service@RequiredArgsConstructor
public class UserService {
    @Autowired
    private ModelMapper modelMapper;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private final EmailService emailService;
    private final ChangePasswordTokenRepository changePasswordTokenRepository;
    private final ConfirmEmailTokenRepository confirmEmailTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final LocalityService localityService;

    public void register(UserRegisterDTO userRegisterDTO) throws UserException {
        validateUser(userRegisterDTO);
        User user = modelMapper.map(userRegisterDTO, User.class);

        userRepository.save(user);
    }

    private void validateUser(UserRegisterDTO user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("An account with this email address already exists. " +
                    "Please try logging in or use a different email address");
        }

        if (userRepository.existsByCnp(user.getCnp())) {
            throw new ResourceAlreadyExistsException("An account with this cnp already exists. " +
                    "Please try logging in that account or change the password if you forgot it");
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

    public void updateUser(HttpServletRequest request, UserEditDTO userEditDTO) {
        User userById = userRepository.findById(userEditDTO.getId()).orElse(null);
        User userByCnp = userRepository.findByCnp(userEditDTO.getCnp()).orElse(null);
        String jwt = jwtUtils.getJwtFromCookies(request);
        User userByEmail = null;

        if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
            String email = jwtUtils.getEmailFromJwtToken(jwt);
            userByEmail = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(new String("Userul nu exista")));;
        }

        if(userById != null  && userByEmail != null){
            if(userById.getId() != userByEmail.getId()){
                throw new IllegalStateException(new String("Adresa de email sau cnp-ul sunt deja folosite."));
            }
            if(userByCnp != null && userById.getId() != userByCnp.getId()){
             throw new IllegalStateException(new String("Adresa de email sau cnp-ul sunt deja folosite."));
            }
        }

        User newUser  = modelMapper.map(userEditDTO, User.class);
        if(userById.getEmail().equals(newUser.getEmail())){
            newUser.setIsEmailConfirmed(userById.getIsEmailConfirmed());
        }
        else newUser.setIsEmailConfirmed(false);

        if(userById.getFirstName().equals(newUser.getFirstName()) && userById.getLastName().equals(newUser.getLastName())){
            newUser.setIsIdentityVerified(userById.getIsIdentityVerified());
        }else   newUser.setIsIdentityVerified(false);


        newUser.setConfirmEmailToken(null);
        newUser.setStripeSession(null);
        newUser.setChangePasswordToken(null);
        newUser.setPassword(userById.getPassword());
        newUser.setRoles(userById.getRoles());
        userRepository.save(newUser);
    }

    public Boolean isUserAllowedToVote(HttpServletRequest request){
        User user = getUser(request);
        if(!user.getIsIdentityVerified() || !user.getIsEmailConfirmed()){
            return false;
        }
        return true;
    }

    public Locality getAddress(HttpServletRequest request) {
        User user = getUser(request);
        return localityService.getLocalityById(user.getLocalityId());
    }

    public void sendForgotPasswordEmail(EmailRequest emailRequest){
        User user = userRepository.findByEmail(emailRequest.getEmail()).orElseThrow(() -> new EntityNotFoundException("Eroare."));
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
        SimpleMailMessage mailMessage = createForgotPasswordEmail(user.getEmail(), changePasswordToken.getChangePasswordToken());
        emailService.sendEmail(mailMessage);

    }

    public void sendChangePasswordEmail(String token, ChangePasswordRequest password){
        ChangePasswordToken changePasswordToken = changePasswordTokenRepository.findByChangePasswordToken(token);
        User user = changePasswordToken.getUser();
        user.setPassword(passwordEncoder.encode(password.getPassword()));
        user.setChangePasswordToken(null);
        userRepository.save(user);
        changePasswordTokenRepository.delete(changePasswordToken);
        SimpleMailMessage mailMessage = createChangePasswordEmail(user.getEmail());
        emailService.sendEmail(mailMessage);
    }

    public boolean sendValidateEmail(HttpServletRequest request, EmailRequest emailRequest){
        User user  = getUser(request);
        if(!emailRequest.getEmail().equals(user.getEmail())){
            throw new IllegalStateException(new String("Eroare"));
        }
        ConfirmEmailToken token = confirmEmailTokenRepository.findByUserId(user.getId()).orElse(null);
        if(token == null){
            token = new ConfirmEmailToken(user);
            confirmEmailTokenRepository.save(token);
        }

        SimpleMailMessage mailMessage =  createConfirmEmailAddress(user.getEmail(), token.getConfirmEmailToken());
        emailService.sendEmail(mailMessage);
        return true;
    }

    public void validateEmailToken(HttpServletRequest request,  String tokenEmail) throws IllegalStateException {
        User user  = getUser(request);
        ConfirmEmailToken token = confirmEmailTokenRepository.findByUserId(user.getId()).orElse(null);

        if(token == null && !user.getIsEmailConfirmed())
            throw new IllegalStateException("Eroare");
        else if(token != null && token.getConfirmEmailToken().equals(tokenEmail) && !user.getIsEmailConfirmed()){
            user.setIsEmailConfirmed(true);
            user.setConfirmEmailToken(null);
            userRepository.save(user);
            confirmEmailTokenRepository.deleteById(token.getTokenId());
            SimpleMailMessage mailMessage = createConfirmedEmail(user.getEmail());
            emailService.sendEmail(mailMessage);
        }
    }

    public boolean isEmailSent(HttpServletRequest request){
        User user  = getUser(request);
        Optional<ConfirmEmailToken> token = confirmEmailTokenRepository.findByUserId(user.getId());
        if(token.isEmpty()) return false;
        return true;
    }

    private SimpleMailMessage createForgotPasswordEmail(String email, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Schimba parola!");
        mailMessage.setText("Pentru a schimba parola, acceseaza link-ul : "
                +"http://127.0.0.1:3000/change-password?token="+ token);
        return mailMessage;
    }

    private SimpleMailMessage createChangePasswordEmail(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Confirmare schimbare parola!");
        mailMessage.setText("Parola a fost schimbata. Daca nu ati schimbat parola, accesati link-ul urmator pentru a va schimba parola in siguranta : "
                +"http://127.0.0.1:3000/forgot-password");
        return mailMessage;
    }

    private SimpleMailMessage createConfirmEmailAddress(String email, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Confirma adresa de email a contului!");
        mailMessage.setText("Pentru a confirma adresa de email, acceseaza link-ul urmator: "
                +"http://127.0.0.1:3000/user/validate/email?token="+ token);
        return mailMessage;
    }

    private SimpleMailMessage createConfirmedEmail(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Confirmare adresa de email!");
        mailMessage.setText("Adresa de email a fost validata. Va multumim!");
        return mailMessage;
    }
}