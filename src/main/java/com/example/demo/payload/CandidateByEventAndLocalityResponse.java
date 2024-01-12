package com.example.demo.payload;

import lombok.*;

@Getter
@Setter
@Builder@Data
@NoArgsConstructor@AllArgsConstructor
public class CandidateByEventAndLocalityResponse {
    private Integer id;
    private String name;
    private String position;
    private Long competingInLocality;
    private String politicalParty;
    private Integer politicalPartyId;
    private String locality;
    private Boolean registered;
}
