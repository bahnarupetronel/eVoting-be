package com.example.demo.payload;

import lombok.*;

@Getter
@Setter
@Builder@Data
@NoArgsConstructor@AllArgsConstructor
public class CandidateByEventAndLocalityResponse {
    private Long id;
    private String name;
    private String position;
    private Long competingInLocality;
    private Long electionId;
    private String politicalParty;
    private Long politicalPartyId;
    private Boolean registered;
}
