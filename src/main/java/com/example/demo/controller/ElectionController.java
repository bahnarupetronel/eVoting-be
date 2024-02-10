package com.example.demo.controller;

import com.example.demo.dto.ElectionDTO;
import com.example.demo.model.CandidateType;
import com.example.demo.model.Election;
import com.example.demo.model.ElectionType;
import com.example.demo.payload.ElectionAndTypesResponse;
import com.example.demo.payload.ElectionCompetitorRequest;
import com.example.demo.payload.ElectionPublish;
import com.example.demo.service.ElectionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/election")
public class ElectionController {
    @Autowired
    ElectionService electionService;

    @GetMapping("/published")
    @ResponseStatus(HttpStatus.OK)
    public  List<Election> getPublishedElections(){
        return electionService.getPublishedElections();
    }

    @GetMapping("/unpublished")
    @ResponseStatus(HttpStatus.OK)
    public  List<Election> getUnpublishedElections(){
        return electionService.getUnpublishedElections();
    }

    @GetMapping("/live")
    @ResponseStatus(HttpStatus.OK)
    public  List<Election> getLiveElections(){
        return electionService.getLiveElections();
    }

    @GetMapping("/finished")
    @ResponseStatus(HttpStatus.OK)
    public  List<Election> getFinishedElections( ){
        return electionService.getFinishedElections();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Election getById( HttpServletRequest request, @PathVariable("id") String id) throws NumberFormatException{
        return electionService.getElectionById(id, request);
    }

    @GetMapping("/upcoming")
    @ResponseStatus(HttpStatus.OK)
    public  List<Election> getUpcomingElections(){
        return electionService.getUpcomingElections();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEvent(@RequestBody ElectionDTO electionDTO){
        electionService.addElection(electionDTO);
    }

    @PostMapping("/publish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void publishEvent(@RequestBody ElectionPublish electionPublish){
        electionService.publishEvent(electionPublish);
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidateFromEvent(@RequestBody Long electionId){
        electionService.deleteElection(electionId);
    }

    @GetMapping("/types")
    @ResponseStatus(HttpStatus.OK)
    public List<ElectionType> getTypes(){
        return electionService.getTypes();
    }

    @GetMapping("/election-and-types")
    @ResponseStatus(HttpStatus.OK)
    public ElectionAndTypesResponse getTypes(@RequestParam("election") String electionId){
        return electionService.getElectionAndTypes(electionId);
    }
}
