package com.example.demo.service;

import com.amazonaws.services.cloudfront.model.PreconditionFailedException;
import com.amazonaws.services.fsx.model.BadRequestException;
import com.example.demo.model.Candidate;
import com.example.demo.model.Election;
import com.example.demo.model.User;
import com.example.demo.model.VoteReferendum;
import com.example.demo.payload.VoteReferendumRequest;
import com.example.demo.repository.VoteReferendumRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service@RequiredArgsConstructor
public class VoteReferendumService {
    @Autowired
    private ModelMapper modelMapper;
    private final UserService userService;
    private final VoteReferendumRepository voteReferendumRepository;
    private final ElectionService electionService;
    private final HasUserVotedService hasUserVotedService;

    public List<VoteReferendum> getAllVotes() {
        return voteReferendumRepository.findAll();
    }

    public void castVote(HttpServletRequest request,VoteReferendumRequest voteReferendumRequest) {
        User user = userService.getUser(request);
        if(!user.getIsIdentityVerified() && !user.getIsEmailConfirmed()){
            throw new PreconditionFailedException(new String("User-ul nu si-a verificat adresa de email si identitatea."));
        }
        Election election = electionService.getElectionById(voteReferendumRequest.getElectionId());


        if(! isElectionLive(election) )
            throw new BadRequestException(new String("Votul nu poate fi efectuat, votarea s-a incheiat"));

        if(! election.isPublished() )
            throw new BadRequestException(new String("Votarea nu este publica. Nu se pot inregistra voturi."));

        VoteReferendum voteReferendum = new VoteReferendum();
        voteReferendum.setElectionId(voteReferendumRequest.getElectionId());
        voteReferendum.setOptionId(voteReferendumRequest.getOptionId());
        voteReferendumRepository.save(voteReferendum);
        hasUserVotedService.registerReferendumVote(user, voteReferendumRequest);
    }

    public List<?> getNumberOfVotes(Long electionId){
        return voteReferendumRepository.countVotesPerOption(electionId);
    }

    private Boolean isElectionLive (Election election){
        LocalDateTime now= getLocalDateTime();

        if(election.getStartDate().compareTo(now) < 0 && now.compareTo(election.getEndDate()) < 0){
            return true;
        }
        return false;
    }

    private LocalDateTime getLocalDateTime() {
        LocalDateTime today = LocalDateTime.now();
        return today;
    }
}
