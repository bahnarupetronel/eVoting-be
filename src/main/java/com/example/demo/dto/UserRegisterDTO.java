package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    private String name;
    private String email;
    private String password;
    private String cnp;
    private String seriesAndNumber;
    private String addressLine1;
    private String addressLine2;
    private String phoneNumber;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    private String linkCIPhoto;
}
