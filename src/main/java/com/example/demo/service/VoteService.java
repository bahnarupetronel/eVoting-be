package com.example.demo.service;

import com.amazonaws.services.cloudfront.model.PreconditionFailedException;
import com.amazonaws.services.fsx.model.BadRequestException;
import com.amazonaws.services.qldb.model.ResourceAlreadyExistsException;
import com.example.demo.dto.VoteDTO;
import com.example.demo.model.*;
import com.example.demo.payload.NumberOfVotesRequest;
import com.example.demo.repository.VoteRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service@RequiredArgsConstructor
public class VoteService {
    @Autowired
    private ModelMapper modelMapper;
    private final VoteRepository voteRepository;
    private final HasUserVotedService hasUserVotedService;
    private final UserService userService;
    private final ElectionService electionService;
    private final LocalityService localityService;

    public void registerVote(HttpServletRequest request, VoteDTO voteDTO){
        User user = userService.getUser(request);
        if(!user.getIsIdentityVerified() && !user.getIsEmailConfirmed()){
            throw new PreconditionFailedException(new String("User-ul nu si-a verificat adresa de email si identitatea."));
        }

        HasUserVoted hasUserVoted = hasUserVotedService.findVoteByUser(user, voteDTO);
        if(hasUserVoted != null){{
            throw new ResourceAlreadyExistsException(new String("User-ul are un vot inregistrat."));
        }}

        Election election = electionService.getElectionById(voteDTO.getElectionId());

        if(! isElectionLive(election) )
            throw new BadRequestException(new String("Votul nu poate fi efectuat, votarea s-a incheiat"));

        if(! election.isPublished() )
            throw new BadRequestException(new String("Votarea nu este publica. Nu se pot inregistra voturi."));

        Vote vote  = new Vote();
        vote.setElectionId(voteDTO.getElectionId());
        vote.setCandidateTypeId(voteDTO.getCandidateTypeId());
        vote.setPoliticalPartyId(voteDTO.getPoliticalPartyId());
        voteRepository.save(vote);
        hasUserVotedService.registerUserVote(user, voteDTO);
    }

    public List<?> getNumberOfVotes(Long electionId, Integer candidateTypeId, Long localityId){
        if(candidateTypeId == 1 || candidateTypeId == 3){
            return getResultsByLocality(electionId, candidateTypeId, localityId);
        }
        if(candidateTypeId == 2 || candidateTypeId == 8){
            return getResultsByCounty(electionId, candidateTypeId, localityId);
        }
        else return getResultsByCountry(electionId, candidateTypeId);
    }

    private List<?> getResultsByLocality (Long electionId, Integer candidateTypeId, Long localityId){
        return  voteRepository.countVotesPoliticalPartyLocality(electionId, candidateTypeId, localityId);
    }

    private List<?> getResultsByCounty (Long electionId, Integer candidateTypeId, Long localityId){
        Locality locality = localityService.getLocalityById(localityId);
        return  voteRepository.countVotesPoliticalPartyCounty(electionId, candidateTypeId, locality.getCounty());
    }

    private List<?> getResultsByCountry (Long electionId, Integer candidateTypeId){
        return  voteRepository.countVotesPoliticalPartyCountry(electionId, candidateTypeId);
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
