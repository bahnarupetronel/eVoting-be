package com.example.demo.exception;

public class InvalidSpecialityIdException extends RuntimeException {
    public InvalidSpecialityIdException(String message) {
        super(message);
    }
}
