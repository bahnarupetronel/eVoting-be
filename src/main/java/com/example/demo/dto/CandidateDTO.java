package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDTO {
    private String name;
    private String birthDate;
    private String gender;
    private String position;
    private String experience;
    private String imageUrl;
    private Integer politicalPartyId;
    private Integer competingIn;
    private String address;
    private String email;
    private String description;
    private String phoneNumber;
}
