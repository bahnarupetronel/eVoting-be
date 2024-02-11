package com.example.demo.payload;

import com.example.demo.model.CandidateType;
import com.example.demo.model.Election;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VotesResponse {
    private Long voteId;
    private Election election;
    private CandidateType candidateType;
}
