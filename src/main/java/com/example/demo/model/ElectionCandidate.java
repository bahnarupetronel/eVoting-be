package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Getter@Setter@RequiredArgsConstructor
@Entity
@Table(name = "election_candidates")
public class ElectionCandidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "election_candidate_id")
    private Long id;

    @Column(name = "election_id",insertable = false, updatable = false)
    @JsonIgnore
    private Long electionId;

    @Column(name = "competing_in_locality",insertable = false, updatable = false)
    @JsonIgnore
    private Long localityId;

    @Column(name = "political_party_id",insertable = false, updatable = false)
    @JsonIgnore
    private Long politicalPartyId;

    @Column(name = "candidate_id",insertable = false, updatable = false)
    @JsonIgnore
    private Long candidateId;

    @ManyToOne(cascade= {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "election_id")
    private Election election;

    @ManyToOne(cascade= {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "competing_in_locality")
    @JsonIgnore
    private Locality locality;

    @ManyToOne(cascade= {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "political_party_id")
    private PoliticalParty politicalParty;

    @ManyToOne(cascade= {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    public ElectionCandidate(Candidate candidate, Election election, PoliticalParty politicalParty, Locality locality) {
        this.candidate = candidate;
        this.election = election;
        this.politicalParty = politicalParty;
        this.locality = locality;
    }
}
