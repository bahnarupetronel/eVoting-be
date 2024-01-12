package com.example.demo.controller;

import com.example.demo.dto.ElectionDTO;
import com.example.demo.model.Election;
import com.example.demo.model.ElectionType;
import com.example.demo.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/election")
public class ElectionController {
    @Autowired
    ElectionService electionService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public  List<Election> getElections(){
        return electionService.getElections();
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
    public Election getById( @PathVariable("id") String id) throws NumberFormatException{
        Long electionId = 0L;
        try{
            electionId   =  Long.parseLong(id);
        }catch (NumberFormatException e) {
            throw new NumberFormatException(new String("Evenimentul cu id-ul " + id + " nu exista! Id-ul nu are formatul corect."));
        }

        return electionService.getElectionById(electionId);
    }

    @GetMapping("/upcoming")
    @ResponseStatus(HttpStatus.OK)
    public  List<Election> getUpcomingElections(){
        return electionService.getUpcomingElections();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addElectionEvent(@RequestBody ElectionDTO electionDTO){
        electionService.addElection(electionDTO);
    }

    @GetMapping("/types")
    @ResponseStatus(HttpStatus.OK)
    public List<ElectionType> getTypes(){
        return electionService.getTypes();
    }
}
