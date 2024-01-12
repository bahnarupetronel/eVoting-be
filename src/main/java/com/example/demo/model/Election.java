package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
@Table(name = "election")
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "election_id")
    private Long electionId;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(name = "type_id", nullable = false)
    @JsonIgnore
    private Long typeId;

    @Column(name = "published", nullable = false)
    private boolean published = false;

    @ManyToOne
    @JoinColumn(insertable=false, updatable=false, name = "type_id", referencedColumnName = "id")
    private ElectionType type;

    @OneToMany(mappedBy = "election")
    @JsonIgnore
    private List<ElectionCandidate> candidates = new ArrayList<>();
}