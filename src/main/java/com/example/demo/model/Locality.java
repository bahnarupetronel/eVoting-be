package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Getter @Setter@AllArgsConstructor@NoArgsConstructor
@Entity
@Table(name = "localities")
public class Locality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "diacritics", length = 100)
    private String diacritics;

    @Column(name = "county", length = 100)
    private String county;

    @Column(name = "zip", length = 100)
    private String zip;

    @Column(name = "auto", length = 100)
    private String auto;

    @Column(name = "population")
    private Integer population;

    @Column(name = "lng")
    private double lng;

    @Column(name = "lat")
    private double lat;
}
