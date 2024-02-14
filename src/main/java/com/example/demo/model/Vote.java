package com.example.demo.model;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
@Entity
@Table(name = "vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long voteId;

    @Column(name = "election_id", nullable = false)
    private Long electionId;

    @Column(name = "candidate_type_id", nullable = false)
    private Long candidateTypeId;

    @Column(name = "political_party_id", nullable = false)
    private Long politicalPartyId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id", referencedColumnName = "election_id", insertable = false, updatable = false)
    @Transient
    private Election election;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Transient
    private CandidateType eventType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "political_party_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Transient
    private PoliticalParty politicalParty;
}