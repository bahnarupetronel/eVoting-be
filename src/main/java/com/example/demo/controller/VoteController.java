package com.example.demo.controller;

import com.example.demo.dto.VoteDTO;
import com.example.demo.service.VoteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController@AllArgsConstructor
@RequestMapping("/api/vote")
public class VoteController {
    private final VoteService voteService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void  registerVote(HttpServletRequest request, @RequestBody VoteDTO voteDTO){
        voteService.registerVote(request, voteDTO);
    }
}
