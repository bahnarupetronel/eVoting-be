package com.example.demo.payload;

import lombok.*;
@Getter @Setter
@Builder @Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateByEventResponse {
        private Integer id;
        private String name;
        private String politicalParty;
        private String locality;

}
