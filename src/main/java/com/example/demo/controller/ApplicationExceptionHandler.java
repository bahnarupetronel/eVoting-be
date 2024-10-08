package com.example.demo.controller;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.amazonaws.services.cloudfront.model.PreconditionFailedException;
import com.amazonaws.services.fsx.model.BadRequestException;
import com.amazonaws.services.glue.model.EntityNotFoundException;
import com.amazonaws.services.qldb.model.ResourceAlreadyExistsException;
import com.stripe.exception.StripeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.naming.InvalidNameException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleInternalServerError( Exception exception,
                                               WebRequest request){
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleResourceNotFound(
            ResourceNotFoundException exception,
            WebRequest request
    ){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEntityNotFoundException(
            EntityNotFoundException exception,
            WebRequest request
    ){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadRequest(
            BadRequestException exception,
            WebRequest request
    ){
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleNumberFormatException(
            NumberFormatException exception,
            WebRequest request
    ){
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(exception.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException exception,
            WebRequest request
    ){
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(
            DataIntegrityViolationException exception,
            WebRequest request
    ){
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(exception.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleIllegalStateException(
            IllegalStateException exception,
            WebRequest request
    ){
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(StripeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleStripeException(
            StripeException exception,
            WebRequest request
    ){
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadCredentialsException(
            BadCredentialsException exception,
            WebRequest request
    ){
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Incorrect password or email!");
    }

    @ExceptionHandler(PreconditionFailedException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<Object> handlePreconditionFailedException(
            PreconditionFailedException exception,
            WebRequest request
    ){
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.PRECONDITION_FAILED)
                .body("Contul nu este verificat");
    }

    @ExceptionHandler(InvalidNameException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<Object> handleInvalidNameException(
            InvalidNameException exception,
            WebRequest request
    ){
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.PRECONDITION_FAILED)
                .body("Datele personale nu se potrivesc cu cele de pe cartea de identitate. Actualizati datele si incercati din nou.");
    }
}
