package com.example.demo.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.amazonaws.services.qldb.model.ResourceAlreadyExistsException;
import com.example.demo.model.*;
import com.example.demo.payload.CandidateByEventAndLocalityResponse;
import com.example.demo.payload.ElectionCompetitorRequest;
import com.example.demo.payload.RegisteredCandidates;
import com.example.demo.repository.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.utils.Convert.convertToLong;

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
        ElectionCandidate electionCandidate = modelMapper.map(electionCompetitorRequest, ElectionCandidate.class);
        if(candidate != null && election != null){
            Optional<ElectionCandidate> existingEntry = electionCandidateRepository.findByCandidateIdAndElectionId(electionCandidate.getCandidateId(), electionCandidate.getElectionId());

            if (existingEntry.isPresent()) {
                throw( new ResourceAlreadyExistsException(new String("Candidatul " + candidate.getName() + "este deja inregistrat pentru evenimentul" + election.getType().getName())));
            } else {
                ElectionCandidate electionCandidateDTO = new ElectionCandidate(candidate, election, politicalParty, locality);
                electionCandidateRepository.save(electionCandidateDTO);
            }
        }
    }

    public void removeCandidateFromEvent(ElectionCompetitorRequest electionCompetitorRequest) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Candidate candidate = candidateRepository.findById(electionCompetitorRequest.getCandidateId()).orElse(null);;
        Election election = electionRepository.findById(electionCompetitorRequest.getElectionId()).orElse(null);;
        if(candidate != null && election != null){
            Optional<ElectionCandidate> existingEntry = electionCandidateRepository.findByCandidateIdAndElectionId(electionCompetitorRequest.getCandidateId(), electionCompetitorRequest.getElectionId());

            if (existingEntry.isPresent()) {
                electionCandidateRepository.deleteById(existingEntry.get().getId());
            } else {
                throw( new ResourceNotFoundException(new String("Candidatul " + candidate.getName() + "nu este inregistrat pentru evenimentul" +election.getType().getName())));
            }
        }
    }

    public List<?> getElectionCandidatesByEvent(Long electionId){
        return electionCandidateRepository.findByElectionId(electionId);
    }

    public List<RegisteredCandidates> getRegisteredPoliticalParties(String electionId, String localityId, String typeId){
        Long election = convertToLong(electionId);
        Long locality = convertToLong(localityId);
        Long type = convertToLong(typeId);
        List<ArrayList<?>> candidatesResponse = electionCandidateRepository.findRegisteredCandidates(election, locality, type);
        return candidatesResponse.stream().map(candidate -> mapToRegisteredCandidate(candidate)).toList();
    }

    public Boolean getRegisteredCandidatesCount(String electionId){
        Long election = convertToLong(electionId);
        return electionCandidateRepository.existsByElectionId(election);
    }

    public List<?> getElectionCandidates(){
        return electionCandidateRepository.findAll();
    }

    private RegisteredCandidates mapToRegisteredCandidate(ArrayList<?> candidate) {
        return  new RegisteredCandidates().builder()
                .candidateId((Integer) candidate.get(0))
                .candidateName((String) candidate.get(1))
                .politicalParty((String) candidate.get(2))
                .electionCandidateId((Integer) candidate.get(3))
                .build();
    }
}
