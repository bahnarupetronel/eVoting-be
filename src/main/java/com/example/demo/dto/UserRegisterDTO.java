package com.example.demo.dto;

import com.example.demo.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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
    private Integer localityId;
    private Integer countyId;
    private String country;
    private Set<String> role;
}
