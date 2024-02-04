package com.example.demo.payload;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredCandidates {
    private Integer candidateId;
    private Integer electionCandidateId;
    private String candidateName;
    private String politicalParty;
}
