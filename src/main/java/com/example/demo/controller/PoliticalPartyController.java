package com.example.demo.controller;

import com.amazonaws.services.pinpoint.model.MessageResponse;
import com.example.demo.dto.PoliticalPartyDTO;
import com.example.demo.model.PoliticalParty;
import com.example.demo.repository.PoliticalPartyRepository;
import com.example.demo.service.PoliticalPartyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/political-party")
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

    @PostMapping("")
    public ResponseEntity<?> addNewPoliticalParty(@RequestBody PoliticalPartyDTO politicalPartyDTO){
        politicalPartyService.addPoliticalParty(politicalPartyDTO);
        return  ResponseEntity.ok(new MessageResponse());
    }
}
