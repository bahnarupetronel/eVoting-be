package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "phone_number", length = 100)
    private String phoneNumber;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "political_party_id")
    private Integer politicalPartyId;

    @Column(name = "competing_in_locality")
    private Integer competingInLocality;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true )
    private List<Education> education = new ArrayList<>();

    @Column(name = "candidate_type_id")
    @JsonIgnore
    private Long candidateTypeId;

    @Column(name = "event_type_id")
    @JsonIgnore
    private Long eventTypeId;

    @ManyToOne
    @JoinColumn(name = "candidate_type_id", insertable = false, updatable = false)
    private CandidateType candidateType;

    @ManyToOne
    @JoinColumn(name = "event_type_id", insertable = false, updatable = false)
    private ElectionType eventType;

//    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Election> election = new ArrayList<>();
}
