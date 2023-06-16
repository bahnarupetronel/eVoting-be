package com.example.demo.controller;

import com.example.demo.model.PoliticalParty;
import com.example.demo.repository.PoliticalPartyRepository;
import com.example.demo.service.PoliticalPartyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/political-parties")
public class PoliticalPartyController {
    private final PoliticalPartyRepository politicalPartyRepository;
    private final PoliticalPartyService politicalPartyService;

    public PoliticalPartyController(PoliticalPartyRepository politicalPartyRepository, PoliticalPartyService politicalPartyService) {
        this.politicalPartyRepository = politicalPartyRepository;
        this.politicalPartyService = politicalPartyService;
    }

    @GetMapping("/all")
    public List<PoliticalParty> getPoliticalParties(){
        return politicalPartyRepository.findAll();
    }

    @PostMapping("/import")
    public void createFakeParties() throws Exception {
        politicalPartyService.associateLocationToParty();
    }
}
