package com.example.demo.controller;

import com.example.demo.dto.PoliticalPartyDTO;
import com.example.demo.model.PoliticalParty;
import com.example.demo.repository.PoliticalPartyRepository;
import com.example.demo.service.PoliticalPartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController@RequiredArgsConstructor
@RequestMapping("/api/political-party")
public class PoliticalPartyController {
    private final PoliticalPartyService politicalPartyService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<PoliticalParty> getPoliticalParties(){
        return politicalPartyService.getPoliticalParties();
    }

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createFakeParties() throws Exception {
        politicalPartyService.associateLocationToParty();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addNewPoliticalParty(@RequestBody PoliticalPartyDTO politicalPartyDTO){
        politicalPartyService.addPoliticalParty(politicalPartyDTO);
    }
}
