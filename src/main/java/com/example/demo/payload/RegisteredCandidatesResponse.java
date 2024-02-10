package com.example.demo.payload;

import com.example.demo.model.Candidate;
import lombok.*;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredCandidatesResponse {
    Candidate candidate;
}
