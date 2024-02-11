package com.example.demo.controller;

import com.example.demo.dto.VoteDTO;
import com.example.demo.payload.NumberOfVotesRequest;
import com.example.demo.service.VoteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController@AllArgsConstructor
@RequestMapping("/api/vote")
public class VoteController {
    private final VoteService voteService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  registerVote(HttpServletRequest request, @RequestBody VoteDTO voteDTO){
        voteService.registerVote(request, voteDTO);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<?> getNumberOfVotes(@RequestParam("electionId") Long electionId, @RequestParam("candidateTypeId") Integer candidateTypeId, @RequestParam("localityId") Long localityId){
       return voteService.getNumberOfVotes(electionId, candidateTypeId, localityId);
    }
}
