package com.example.demo.controller;

import com.example.demo.model.Candidate;
import com.example.demo.model.ElectionCandidate;
import com.example.demo.payload.ElectionCompetitorRequest;
import com.example.demo.payload.RegisteredCandidates;
import com.example.demo.payload.RegisteredCandidatesResponse;
import com.example.demo.service.ElectionCandidateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/election-candidate")
public class ElectionCandidateController {
    @Autowired
    ElectionCandidateService electionCandidateService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addElectionCandidate( @RequestBody ElectionCompetitorRequest electionCompetitorRequest){
        electionCandidateService.addElectionCompetitor(electionCompetitorRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<?> getElectionCandidates(){
        return electionCandidateService.getElectionCandidates();
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<RegisteredCandidates> getRegisteredPoliticalParties(@RequestParam("election") String electionId, @RequestParam("locality") String localityId, @RequestParam("typeId") String typeId){
        return electionCandidateService.getRegisteredPoliticalParties(electionId, localityId, typeId);
    }

    @GetMapping("/populated")
    @ResponseStatus(HttpStatus.OK)
    public Boolean getRegisteredCandidatesCount(@RequestParam("election") String electionId){
        return electionCandidateService.getRegisteredCandidatesCount(electionId);
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidateFromEvent(@RequestBody ElectionCompetitorRequest electionCompetitorRequest){
        electionCandidateService.removeCandidateFromEvent(electionCompetitorRequest);
    }

    @GetMapping("/registered/single")
    @ResponseStatus(HttpStatus.OK)
    public List<Candidate> getSingleCandidatesPerPoliticalParty(HttpServletRequest request, @RequestParam("electionId") String electionId, @RequestParam("candidateTypeId") String candidateTypeId){
        return electionCandidateService.getSingleCandidateRegistered(request, electionId, candidateTypeId);
    }

    @GetMapping("/registered/multiple")
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, List<Candidate>> getMultipleCandidatesPerPoliticalParty(HttpServletRequest request, @RequestParam("electionId") String electionId, @RequestParam("candidateTypeId") String candidateTypeId){
        return electionCandidateService.getMultipleCandidatesRegistered(request, electionId, candidateTypeId);
    }
}
