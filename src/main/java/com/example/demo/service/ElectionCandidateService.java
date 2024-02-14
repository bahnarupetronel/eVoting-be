package com.example.demo.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.amazonaws.services.qldb.model.ResourceAlreadyExistsException;
import com.example.demo.model.*;
import com.example.demo.payload.ElectionCompetitorRequest;
import com.example.demo.payload.RegisteredCandidates;
import com.example.demo.payload.RegisteredCandidatesResponse;
import com.example.demo.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.demo.utils.Convert.convertToLong;

@Service@RequiredArgsConstructor
public class ElectionCandidateService {
    @Autowired
    private ModelMapper modelMapper;
    private final ElectionCandidateRepository electionCandidateRepository;
    private final CandidateRepository candidateRepository;
    private final PoliticalPartyRepository politicalPartyRepository;
    private final LocalityService localityService;
    private final ElectionService electionService;
    private final UserService userService;
    private final CandidateService candidateService;

    public void addElectionCompetitor(ElectionCompetitorRequest electionCompetitorRequest){
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Candidate candidate = candidateRepository.findById(electionCompetitorRequest.getCandidateId()).orElse(null);
        Election election = electionService.getElectionById(electionCompetitorRequest.getElectionId());
        PoliticalParty politicalParty = politicalPartyRepository.findById(Math.toIntExact(electionCompetitorRequest.getPoliticalPartyId())).orElse(null);
        Locality locality = localityService.getLocalityById(electionCompetitorRequest.getCompetingInLocality());
        ElectionCandidate electionCandidate = modelMapper.map(electionCompetitorRequest, ElectionCandidate.class);
        if(candidate != null && election != null){
            Optional<ElectionCandidate> existingEntry = electionCandidateRepository.findByCandidateIdAndElectionId(electionCandidate.getCandidateId(), electionCandidate.getElectionId());

            if (existingEntry.isPresent()) {
                throw( new ResourceAlreadyExistsException(new String("Candidatul " + candidate.getName() + "este deja inregistrat pentru evenimentul" + election.getType().getName())));
            } else {
                ElectionCandidate electionCandidateDTO = new ElectionCandidate(candidate, election, politicalParty, locality);
                electionCandidateDTO.setCandidateTypeId( electionCompetitorRequest.getCandidateTypeId());
                electionCandidateDTO.setCounty( electionCompetitorRequest.getCounty());
                electionCandidateRepository.save(electionCandidateDTO);
            }
        }
    }

    public void removeCandidateFromEvent(ElectionCompetitorRequest electionCompetitorRequest) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Candidate candidate = candidateRepository.findById(electionCompetitorRequest.getCandidateId()).orElse(null);;
        Election election = electionService.getElectionById(electionCompetitorRequest.getElectionId());;
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

    public Boolean isPoliticalPartyRegistered(Long electionId, Long politicalPartyId, Integer localityId) {
        return electionCandidateRepository.isCandidateRegistered(electionId, politicalPartyId, localityId);
    }

    public List<Candidate> getSingleCandidateRegistered (HttpServletRequest request, String election, String candidateTypeId){
        Long candidateType = convertToLong(candidateTypeId);
        Long electionId = convertToLong(election);
        User user = userService.getUser(request);
        Long userLocality = user.getLocalityId();
        String userCounty = user.getCounty();

        if(candidateType == 1){
            return getSingleRegisteredLocality(electionId, userLocality, candidateType);
        }
        if(candidateType == 2){
            return getSingleRegisteredCounty(electionId, candidateType, userCounty);
        }
        return getSingleRegisteredCountry(electionId, candidateType);

    }

    public Map<Long, List<Candidate>> getMultipleCandidatesRegistered (HttpServletRequest request, String election, String candidateTypeId){
        Long candidateType = convertToLong(candidateTypeId);
        Long electionId = convertToLong(election);
        User user = userService.getUser(request);
        Long userLocality = user.getLocalityId();
        String userCounty = user.getCounty();

        Map<Long , List<Candidate>> candidatesByParty = new HashMap<>();
        List<Candidate> candidates;
        if(candidateType == 3){
            candidates = getSingleRegisteredLocality(electionId, userLocality, candidateType);
        }
        else if(candidateType == 4 || candidateType == 5 || candidateType == 8){
            candidates =  getSingleRegisteredCounty(electionId, candidateType, userCounty);
        }
        else candidates =  getSingleRegisteredCountry(electionId, candidateType);

        for (Candidate candidate : candidates) {
            if (!candidatesByParty.containsKey(candidate.getPoliticalPartyId())) {
                candidatesByParty.put(candidate.getPoliticalPartyId(), new ArrayList<>());
            }
            candidatesByParty.get(candidate.getPoliticalPartyId()).add(candidate);
        }
        return candidatesByParty;

    }

    private List<Candidate> getSingleRegisteredLocality(Long electionId, Long localityId, Long candidateTypeId) {
        //id 1 and id 3 //Primar sau consiliu local
        List<Long> list = electionCandidateRepository.findByElectionIdAndCompetingInLocality(electionId, localityId, candidateTypeId);

        List<Candidate> registeredCandidates = new ArrayList<>();
        list.forEach(item -> {
            Candidate candidate = candidateService.getCandidateById(item);
            registeredCandidates.add(candidate);
        });

        return registeredCandidates;
    }

    private List<Candidate> getSingleRegisteredCounty(Long electionId, Long candidateTypeId, String county) {
        //id 2 and id 8 //Consiliu judetean sau Presedinte consiliu judetean
        List<Long> list = electionCandidateRepository.findByElectionIdAndCompetingInCounty(electionId, candidateTypeId, county);
        List<Candidate> registeredCandidates = new ArrayList<>();
        list.forEach(item -> {
            Candidate candidate = candidateService.getCandidateById(item);
            registeredCandidates.add(candidate);
        });

        return registeredCandidates;
    }

    private List<Candidate> getSingleRegisteredCountry(Long electionId, Long candidateTypeId) {
        //id 4, 5, 6, 7
        List<Long> list = electionCandidateRepository.findByElectionIdAndCandidateTypeId(electionId, candidateTypeId);

        List<Candidate> registeredCandidates = new ArrayList<>();
        list.forEach(item -> {
            Candidate candidate = candidateService.getCandidateById(item);
            registeredCandidates.add(candidate);
        });

        return registeredCandidates;
    }

    private RegisteredCandidates mapToRegisteredCandidate(ArrayList<?> candidate) {
        return  new RegisteredCandidates().builder()
                .candidateId((Long) candidate.get(0))
                .candidateName((String) candidate.get(1))
                .politicalParty((String) candidate.get(2))
                .electionCandidateId((Long) candidate.get(3))
                .build();
    }

}
