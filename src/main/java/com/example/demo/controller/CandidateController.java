package com.example.demo.controller;

import com.amazonaws.services.pinpoint.model.MessageResponse;
import com.example.demo.dto.CandidateDTO;
import com.example.demo.model.Candidate;
import com.example.demo.model.User;
import com.example.demo.payload.CandidateByEventAndLocalityResponse;
import com.example.demo.payload.CandidateByEventResponse;
import com.example.demo.payload.CandidateRequest;
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

    @PostMapping("/list")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUsers() {
        candidateService.createCandidatesFromPoliticalPartyLocality();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Candidate getCandidateById(@PathVariable("id") String id) {
        return candidateService.getCandidateById(Integer.valueOf(id));
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<?> getCandidates(){
        return candidateService.getAllCandidates();
    }

    @GetMapping("/locality/{localityId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Candidate> getCandidatesByLocalityId(@PathVariable("localityId") Long localityId) {
        return candidateService.getCandidatesByLocalityId(localityId);
    }

    @GetMapping("/type/{typeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateByEventResponse> getCandidatesByEventTypeId(@PathVariable("typeId") Integer typeId) {
        return candidateService.getCandidatesByEventTypeId(typeId);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewCandidate(@RequestBody CandidateRequest candidateRequest){
        candidateService.addCandidate(candidateRequest);
    }

    @GetMapping("/filtered")
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateByEventAndLocalityResponse> getCandidatesByEventAndLocality(@RequestParam("typeId") Integer typeId, @RequestParam("localityId") Integer localityId, @RequestParam("eventId") Integer eventId,@RequestParam("candidateTypeId") Integer candidateTypeId) {
        return candidateService.getCandidatesByEventAndLocality(typeId, localityId, eventId, candidateTypeId);
    }
}
