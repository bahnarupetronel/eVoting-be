package com.example.demo.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class AuthRequest {
    @NotNull
    private String email;

    @NotNull
    private String password;
}
