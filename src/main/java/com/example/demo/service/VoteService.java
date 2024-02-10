package com.example.demo.service;

import com.amazonaws.services.cloudfront.model.PreconditionFailedException;
import com.amazonaws.services.fsx.model.BadRequestException;
import com.amazonaws.services.qldb.model.ResourceAlreadyExistsException;
import com.example.demo.dto.VoteDTO;
import com.example.demo.model.*;
import com.example.demo.repository.VoteRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service@RequiredArgsConstructor
public class VoteService {
    @Autowired
    private ModelMapper modelMapper;
    private final VoteRepository voteRepository;
    private final HasUserVotedService hasUserVotedService;
    private final UserService userService;
    private final ElectionService electionService;
    private final CandidateService candidateService;
    private final ElectionCandidateService electionCandidateService;

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

        Candidate candidate = candidateService.getCandidateById(Math.toIntExact(voteDTO.getCandidateId()));

        Boolean  isCandidateRegistered = electionCandidateService.isCandidateRegisterd(voteDTO.getElectionId(), voteDTO.getCandidateId(), candidate.getCompetingInLocality() );

        if(!isCandidateRegistered)
            throw new BadRequestException(new String("Candidatul nu este inregistrat pentru acest eveniment."));

        if(candidate.getCandidateTypeId() != voteDTO.getCandidateTypeId())
            throw new BadRequestException(new String("Candidatul nu este inregistrat pentru acets eveniment"));

        if(! isElectionLive(election) )
            throw new BadRequestException(new String("Votul nu poate fi efectuat, votarea s-a incheiat"));

        if(! election.isPublished() )
            throw new BadRequestException(new String("Votarea nu este publica. Nu se pot inregistra voturi."));

        Vote vote  = new Vote();
        vote.setElectionId(voteDTO.getElectionId());
        vote.setCandidateTypeId(voteDTO.getCandidateTypeId());
        vote.setCandidateId(voteDTO.getCandidateId());
        voteRepository.save(vote);
        hasUserVotedService.registerUserVote(user, voteDTO);
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
