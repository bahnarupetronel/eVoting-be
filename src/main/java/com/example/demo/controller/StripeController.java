package com.example.demo.controller;

import com.example.demo.service.StripeService;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.naming.InvalidNameException;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {
    @Autowired
    private StripeService stripeService;

    @PostMapping("/verification-session")
    @ResponseStatus(HttpStatus.OK)
    public String createVerificationSession(HttpServletRequest request) throws StripeException, InvalidNameException {
        return stripeService.createVerificationSession(request);
    }

    @GetMapping("/verification-session")
    @ResponseStatus(HttpStatus.OK)
    public String getSessions(HttpServletRequest request) throws StripeException, InvalidNameException {
        return stripeService.getVerificationSession(request);
    }
}
