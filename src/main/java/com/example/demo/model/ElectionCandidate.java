package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
@Entity
@Table(name = "election_candidates")
public class ElectionCandidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "election_candidate_id")
    private Long id;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "election_id")
    private Election election;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "competing_in_locality")
    private Locality locality;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "political_party_id")
    private PoliticalParty politicalParty;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    public ElectionCandidate(Candidate candidate, Election election, PoliticalParty politicalParty, Locality locality) {
        this.candidate = candidate;
        this.election = election;
        this.politicalParty = politicalParty;
        this.locality = locality;
    }
}
