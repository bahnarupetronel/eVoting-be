package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NumberOfVotesRequest {
    private Long electionId;
    private Integer candidateTypeId;
    private Long localityId;
}
