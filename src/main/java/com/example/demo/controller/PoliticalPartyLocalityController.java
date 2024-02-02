package com.example.demo.controller;

import com.example.demo.model.PoliticalPartyLocality;
import com.example.demo.service.PoliticalPartyLocalityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/political-parties-localities")
@AllArgsConstructor
public class PoliticalPartyLocalityController {
    private final PoliticalPartyLocalityService politicalPartyLocalityService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<PoliticalPartyLocality> getPoliticalParties(@RequestParam("localityId") String localityId){
        return politicalPartyLocalityService.getPoliticalPartiesByLocality(localityId);
    }
}
