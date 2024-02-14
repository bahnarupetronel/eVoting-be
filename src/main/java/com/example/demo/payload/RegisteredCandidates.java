package com.example.demo.payload;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredCandidates {
    private Long candidateId;
    private Long electionCandidateId;
    private String candidateName;
    private String politicalParty;
}
