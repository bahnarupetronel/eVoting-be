package com.example.demo.controller;

import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.dto.UserRequestDTO;
import com.example.demo.exception.ConfirmPasswordException;
import com.example.demo.exception.InvalidEmailException;
import com.example.demo.exception.InvalidPasswordException;
import com.example.demo.exception.UserException;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO user) {
        try {
            userService.register(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidEmailException e) {
            return new ResponseEntity<>("Invalid email format", HttpStatus.BAD_REQUEST);
        } catch (ConfirmPasswordException e) {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>("Invalid password format. Password must have at least 8 characters", HttpStatus.BAD_REQUEST);
        } catch (UserException e) {
            return new ResponseEntity<>("Unable to register user. Please try again later", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserRequestDTO userRequestDTO){
        try {
            String token=userService.login(userRequestDTO.getEmail(), userRequestDTO.getPassword());
            return ResponseEntity.ok(token);
        }catch (com.example.demo.exception.UserException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}