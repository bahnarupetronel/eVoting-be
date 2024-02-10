package com.example.demo.service;

import com.example.demo.model.VoteReferendum;
import com.example.demo.payload.VoteReferendumRequest;
import com.example.demo.repository.VoteReferendumRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class VoteReferendumService {

    private final VoteReferendumRepository voteReferendumRepository;

    @Autowired
    public VoteReferendumService(VoteReferendumRepository voteReferendumRepository) {
        this.voteReferendumRepository = voteReferendumRepository;
    }

    public List<VoteReferendum> getAllVotes() {
        return voteReferendumRepository.findAll();
    }

    public void castVote(HttpServletRequest request,VoteReferendumRequest voteReferendumRequest) {
        VoteReferendum voteReferendum = new VoteReferendum();
        voteReferendumRepository.save(voteReferendum);
    }
}
