package com.example.demo.controller;

import com.example.demo.dto.VoteDTO;
import com.example.demo.payload.VoteReferendumRequest;
import com.example.demo.service.VoteReferendumService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/referendum-vote")@AllArgsConstructor
public class VoteReferendumController {
    private final VoteReferendumService voteReferendumService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void castReferendumVote(HttpServletRequest request, @RequestBody VoteReferendumRequest voteReferendumRequest){
        voteReferendumService.castVote(request, voteReferendumRequest);
    }

    @GetMapping("/results")
    @ResponseStatus(HttpStatus.OK)
    public List<?> getNumberOfVotes(@RequestParam("electionId") Long electionId){
        return voteReferendumService.getNumberOfVotes(electionId);
    }
}
