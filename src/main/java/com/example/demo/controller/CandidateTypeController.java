package com.example.demo.controller;

import com.example.demo.model.CandidateType;
import com.example.demo.service.CandidateTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate-type")
@RequiredArgsConstructor
public class CandidateTypeController {
    private final CandidateTypeService candidateTypeService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateType> getTypes(){
        return candidateTypeService.getTypes();
    }

    @GetMapping("/election")
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateType> getTypesByElectionType(@RequestParam("election-type") String electionTypeId){
        return candidateTypeService.getTypesByElectionType(electionTypeId);
    }
}
