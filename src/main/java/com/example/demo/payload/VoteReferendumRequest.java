package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteReferendumRequest {
        private Long electionId;
        private Long candidateTypeId;
        private Long optionId;
}
