package com.example.demo.exception;

public class ConfirmPasswordException extends RuntimeException {
    public ConfirmPasswordException(String message) {
        super(message);
    }
}