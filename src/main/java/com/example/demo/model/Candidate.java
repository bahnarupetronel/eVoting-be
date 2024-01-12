package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "gender", length = 100)
    private String gender;

    @Column(name = "position", length = 100)
    private String position;

    @Column(name = "image_url", length = 100)
    private String imageUrl;

    @Column(name = "birth_date", length = 100)
    private String birthDate;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "residence", length = 100)
    private String residence;

    @Column(name = "phone_number", length = 100)
    private String phoneNumber;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "political_party_id")
    private Integer politicalPartyId;

    @Column(name = "competing_in_locality")
    private Integer competingInLocality;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> education = new ArrayList<>();

//    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Election> election = new ArrayList<>();
}
