package com.example.demo.controller;

import com.example.demo.payload.UserEditDTO;
import com.example.demo.model.User;
import com.example.demo.payload.ChangePasswordRequest;
import com.example.demo.payload.EmailRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public User getUserDetails(HttpServletRequest request){
        return userService.getUser(request);
    }

    @GetMapping("/is-logged-in")
    @ResponseStatus(HttpStatus.OK)
    public User isUserLoggedIn(HttpServletRequest request){
        return userService.getUser(request);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void editUserDetails(HttpServletRequest request, @RequestBody UserEditDTO userEditDTO) {
        userService.updateUser(request, userEditDTO);
    }

    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendEmailForPasswordChange(@RequestBody EmailRequest emailRequest){
        userService.sendForgotPasswordEmail(emailRequest);
    }

    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestParam("token") String token, @RequestBody ChangePasswordRequest changePasswordRequest){
        userService.sendChangePasswordEmail(token, changePasswordRequest);
    }

    @PostMapping("/validate-email")
    @ResponseStatus(HttpStatus.OK)
    public boolean sendConfirmEmail(HttpServletRequest request, @RequestBody EmailRequest emailRequest){
        return userService.sendValidateEmail(request, emailRequest);
    }

    @PostMapping("/email-validation-token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmEmail(HttpServletRequest request, @RequestParam("token") String token){
        userService.validateEmailToken(request, token);
    }

    @GetMapping("/confirm-email")
    @ResponseStatus(HttpStatus.OK)
    public boolean isEmailSent(HttpServletRequest request){
        return userService.isEmailSent(request);
    }

}
