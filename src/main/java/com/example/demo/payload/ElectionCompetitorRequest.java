package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class ElectionCompetitorRequest {
    private Long candidateId;
    private Long electionId;
    private Long competingInLocality;
    private Long politicalPartyId;
    private Long candidateTypeId;
    private String county;
}
