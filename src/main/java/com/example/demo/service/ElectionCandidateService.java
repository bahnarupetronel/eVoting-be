package com.example.demo.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.amazonaws.services.qldb.model.ResourceAlreadyExistsException;
import com.example.demo.model.*;
import com.example.demo.payload.ElectionCompetitorRequest;
import com.example.demo.repository.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service@AllArgsConstructor
public class ElectionCandidateService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final ElectionCandidateRepository electionCandidateRepository;

    @Autowired
    private final CandidateRepository candidateRepository;

    @Autowired
    private final PoliticalPartyRepository politicalPartyRepository;

    @Autowired
    private final LocalityRepository localityRepository;

    @Autowired
    private final ElectionRepository electionRepository;

    public void addElectionCompetitor(ElectionCompetitorRequest electionCompetitorRequest){
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Candidate candidate = candidateRepository.findById(electionCompetitorRequest.getCandidateId()).orElse(null);;
        Election election = electionRepository.findById(electionCompetitorRequest.getElectionId()).orElse(null);;
        PoliticalParty politicalParty = politicalPartyRepository.findById(Math.toIntExact(electionCompetitorRequest.getPoliticalPartyId())).orElse(null);;
        Locality locality = localityRepository.findById(Math.toIntExact(electionCompetitorRequest.getCompetingInLocality())).orElse(null);;
        if(candidate != null && election != null){
            Optional<ElectionCandidate> existingEntry = electionCandidateRepository.findByCandidateAndElection(candidate, election);

            if (existingEntry.isPresent()) {
                throw( new ResourceAlreadyExistsException(new String("Candidatul " + candidate.getName() + "este deja inregistrat pentru evenimentul" + election.getTitle())));
            } else {
                ElectionCandidate electionCompetitor = new ElectionCandidate(candidate, election, politicalParty, locality);
                electionCandidateRepository.save(electionCompetitor);
            }
        }
    }

    public void removeCandidateFromEvent(ElectionCompetitorRequest electionCompetitorRequest) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Candidate candidate = candidateRepository.findById(electionCompetitorRequest.getCandidateId()).orElse(null);;
        Election election = electionRepository.findById(electionCompetitorRequest.getElectionId()).orElse(null);;
        if(candidate != null && election != null){
            Optional<ElectionCandidate> existingEntry = electionCandidateRepository.findByCandidateAndElection(candidate, election);

            if (existingEntry.isPresent()) {
                electionCandidateRepository.deleteById(existingEntry.get().getId());
            } else {
                throw( new ResourceNotFoundException(new String("Candidatul " + candidate.getName() + "nu este inregistrat pentru evenimentul" + election.getTitle())));
            }
        }
    }
}
