package com.example.demo.controller;

import com.example.demo.payload.ElectionCompetitorRequest;
import com.example.demo.service.ElectionCandidateService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/election-competitor")
public class ElectionCandidateController {
    @Autowired
    ElectionCandidateService electionCandidateService;

    @Autowired
    UserService userService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addElectionCandidate( @RequestBody ElectionCompetitorRequest electionCompetitorRequest){
        electionCandidateService.addElectionCompetitor(electionCompetitorRequest);
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidateFromEvent(@RequestBody ElectionCompetitorRequest electionCompetitorRequest){
        electionCandidateService.removeCandidateFromEvent(electionCompetitorRequest);
    }
}
