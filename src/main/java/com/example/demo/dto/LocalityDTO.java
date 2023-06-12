package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter@AllArgsConstructor@NoArgsConstructor
public class LocalityDTO {
    private String name;
    private String diacritics;
    private String county;
    private String auto;
    private String zip;
    private Integer population;
    private Double lat;
    private Double lng;
}
