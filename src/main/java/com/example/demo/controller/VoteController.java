package com.example.demo.controller;

import com.example.demo.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetBalance;


@CrossOrigin(origins = "http://localhost:3000")
@RestController@AllArgsConstructor
@RequestMapping("/api/vote")
public class VoteController {
    private final VoteService voteService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Request<?, EthGetBalance> getTypes(){

        Request<?, EthGetBalance> text = voteService.tryEth();
        return text;
    }
}
