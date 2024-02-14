package com.example.demo.payload;

import com.example.demo.dto.EducationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@AllArgsConstructor@RequiredArgsConstructor
public class CandidateRequest {
    private String name;
    private String birthDate;
    private String gender;
    private String position;
    private String imageUrl;
    private Long politicalPartyId;
    private Long competingInLocality;
    private Long candidateTypeId;
    private Long eventTypeId;
    private String address;
    private String email;
    private String description;
    private String phoneNumber;
    private List<EducationDTO> education;
}
