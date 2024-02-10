package com.example.demo.dto;

import com.example.demo.model.CandidateType;
import com.example.demo.model.ElectionCandidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredCandidatesDTO {
    List<ElectionCandidate> electionCandidates;
}
