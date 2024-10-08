package com.example.demo.payload;

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
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String cnp;
    private String seriesAndNumber;
    private String addressLine1;
    private String addressLine2;
    private String phoneNumber;
    private String postalCode;
    private Long localityId;
    private String locality;
    private String county;
}
