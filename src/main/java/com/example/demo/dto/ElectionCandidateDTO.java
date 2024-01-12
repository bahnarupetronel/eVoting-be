package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter@AllArgsConstructor@NoArgsConstructor
public class ElectionCandidateDTO {

    private Long electionId;
    private Long candidateId;
    private Long competingIn;
    private Long politicalPartyId;
}
