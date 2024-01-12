package com.example.demo.controller;

import com.amazonaws.services.pinpoint.model.MessageResponse;
import com.example.demo.dto.CandidateDTO;
import com.example.demo.model.Candidate;
import com.example.demo.model.User;
import com.example.demo.payload.CandidateByEventAndLocalityResponse;
import com.example.demo.payload.CandidateByEventResponse;
import com.example.demo.service.CandidateService;
import com.example.demo.service.EducationService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidate")
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;
    private final EducationService educationService;
    private final UserService userService;

    @PostMapping("/list")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUsers() {
        candidateService.createCandidatesFromPoliticalPartyLocality();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateByName(@PathVariable("id") String name) {
        name = name.replace("-", " ");
        Candidate candidate = candidateService.getCandidateByName(name);
        if (candidate != null) {
            return ResponseEntity.ok(candidate);
        }
        return ResponseEntity.notFound().build();

    }


    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<?> getCandidates(){
        return candidateService.getAllCandidates();
    }

    @GetMapping("/locality/{localityId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Candidate> getCandidatesByLocalityId(@PathVariable("localityId") Long localityId) {
        List<Candidate> candidates = candidateService.getCandidatesByLocalityId(localityId);
        return candidates;
    }

    @GetMapping("/type/{typeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateByEventResponse> getCandidatesByEventTypeId(@PathVariable("typeId") Integer typeId) {
        return candidateService.getCandidatesByEventTypeId(typeId);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewCandidate(CandidateDTO candidateDTO){
        candidateService.addCandidate(candidateDTO);
    }

    @GetMapping("/type/{typeId}/locality/{localityId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateByEventAndLocalityResponse> getCandidatesByEventAndLocality(@PathVariable("typeId") Integer typeId, @PathVariable("localityId") Integer localityId) {
        return candidateService.getCandidatesByEventAndLocality(typeId, localityId);
    }

}
