package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "vote_referendum")
public class VoteReferendum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long voteId;

    @Column(name = "election_id")
    private Long electionId;

    @Column(name = "option_id")
    private Long optionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", referencedColumnName = "option_id", insertable = false, updatable = false)
    @Transient
    private ReferendumOptions referendumOptions;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id", referencedColumnName = "election_id", insertable = false, updatable = false)
    @Transient
    private Election election;


}
