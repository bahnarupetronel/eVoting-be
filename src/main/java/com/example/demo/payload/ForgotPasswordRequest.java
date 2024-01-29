package com.example.demo.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter@RequiredArgsConstructor
public class ForgotPasswordRequest {
    private String email;
}
