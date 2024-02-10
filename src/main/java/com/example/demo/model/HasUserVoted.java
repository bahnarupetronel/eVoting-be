package com.example.demo.model;

import lombok.*;

import jakarta.persistence.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
@Entity
@Table(name = "has_user_voted")
public class HasUserVoted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_vote_id")
    private Long userVoteId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "election_id", nullable = false)
    private Long electionId;

    @Column(name = "candidate_type_id", nullable = false)
    private Long candidateTypeId;

}
