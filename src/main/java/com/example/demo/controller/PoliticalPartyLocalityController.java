package com.example.demo.controller;

import com.example.demo.repository.PoliticalPartyLocalityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/political-parties-localities")
public class PoliticalPartyLocalityController {
    private final PoliticalPartyLocalityRepository politicalPartyLocalityRepository;

    public PoliticalPartyLocalityController(PoliticalPartyLocalityRepository politicalPartyLocalityRepository) {
        this.politicalPartyLocalityRepository = politicalPartyLocalityRepository;
    }

    @GetMapping("/all")
    public List<Integer> getPoliticalParties(){
        return politicalPartyLocalityRepository.getAllPoliticalPartyLocalityIds();
    }

}
