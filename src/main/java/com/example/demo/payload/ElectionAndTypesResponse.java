package com.example.demo.payload;

import com.example.demo.model.CandidateType;
import com.example.demo.model.Election;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class ElectionAndTypesResponse {
    Election election;
    List<CandidateType> candidateTypes;
}
