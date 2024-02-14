package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEditDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
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
