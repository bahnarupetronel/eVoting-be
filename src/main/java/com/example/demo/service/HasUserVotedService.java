package com.example.demo.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.example.demo.dto.HasUserVotedDTO;
import com.example.demo.dto.VoteDTO;
import com.example.demo.model.HasUserVoted;
import com.example.demo.model.User;
import com.example.demo.repository.HasUserVotedRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


import static com.example.demo.utils.Convert.convertToLong;

@Service
@RequiredArgsConstructor
public class HasUserVotedService {
    private final HasUserVotedRepository hasUserVotedRepository;
    private final UserService userService;

    public Boolean findVoteByRequest(HttpServletRequest request,  String electionId,  String candidateTypeId) {
        User user = userService.getUser(request);
        Long id = convertToLong(electionId);
        Long typeId = convertToLong(candidateTypeId);
        HasUserVoted hasUserVoted = new HasUserVoted();
        hasUserVoted.setElectionId(id);
        hasUserVoted.setCandidateTypeId(typeId);
        hasUserVoted.setUserId(user.getId());
        Example<HasUserVoted> example = Example.of(hasUserVoted);
        HasUserVoted hasUserVotedResult = hasUserVotedRepository.findOne(example).orElse(null);
        if(hasUserVotedResult == null)
            return false;
        return true;
    }

    public HasUserVoted findVoteByUser(User user, VoteDTO voteDTO) {
        HasUserVoted hasUserVoted = mapDtoToHasUserVoted(user, voteDTO);
        Example<HasUserVoted> example = Example.of(hasUserVoted);
         HasUserVoted hasUserVotedResult = hasUserVotedRepository.findOne(example).orElse(null);
        return hasUserVotedResult;
    }

    public HasUserVoted registerUserVote (User user, VoteDTO voteDTO){
        HasUserVoted hasUserVoted = new HasUserVoted();
        hasUserVoted.setUserId(user.getId());
        hasUserVoted.setElectionId(voteDTO.getElectionId());
        hasUserVoted.setCandidateTypeId(voteDTO.getCandidateTypeId());
        return hasUserVotedRepository.save(hasUserVoted);
    }

    private HasUserVoted mapToHasUserVoted(User user, HasUserVotedDTO hasUserVotedDTO) {
        HasUserVoted hasUserVoted = new HasUserVoted();
        hasUserVoted.setUserId(user.getId());
        hasUserVoted.setElectionId(hasUserVotedDTO.getElectionId());
        hasUserVoted.setCandidateTypeId(hasUserVotedDTO.getCandidateTypeId());
        return hasUserVoted;
    }

    private HasUserVoted mapDtoToHasUserVoted(User user, VoteDTO voteDTO) {
        HasUserVoted hasUserVoted = new HasUserVoted();
        hasUserVoted.setUserId(user.getId());
        hasUserVoted.setElectionId(voteDTO.getElectionId());
        hasUserVoted.setCandidateTypeId(voteDTO.getCandidateTypeId());
        return hasUserVoted;
    }
}
