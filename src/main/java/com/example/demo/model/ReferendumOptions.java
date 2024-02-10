package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter@Setter@RequiredArgsConstructor
@Entity
@Table(name = "referendum_options")
public class ReferendumOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    @Column(name = "option_name", nullable = false)
    private String optionName;
}
