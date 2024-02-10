package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter@AllArgsConstructor@NoArgsConstructor
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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

    @ManyToMany(mappedBy = "localities")
    @JsonIgnore
    private List<PoliticalParty> politicalParties = new ArrayList<>();
}
