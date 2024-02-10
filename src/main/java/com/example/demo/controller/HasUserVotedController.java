package com.example.demo.controller;

import com.example.demo.dto.HasUserVotedDTO;
import com.example.demo.service.HasUserVotedService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
@RequestMapping("/api/user-voted")
public class HasUserVotedController {
    private HasUserVotedService hasUserVotedService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Boolean  getHasUserVoted(HttpServletRequest request, @RequestParam("electionId") String electionId, @RequestParam("candidateTypeId") String candidateTypeId){
        return hasUserVotedService.findVoteByRequest(request, electionId, candidateTypeId);
    }
}
