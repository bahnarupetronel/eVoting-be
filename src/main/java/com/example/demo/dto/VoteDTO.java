package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class VoteDTO {
    private Long electionId;
    private Long candidateTypeId;
    private Long politicalPartyId;
}
