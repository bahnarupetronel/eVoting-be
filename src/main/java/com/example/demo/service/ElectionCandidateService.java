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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Locality locality = localityService.getLocalityById(Math.toIntExact(electionCompetitorRequest.getCompetingInLocality()));
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

    public Boolean isCandidateRegisterd(Long electionId, Long candidateId, Integer localityId) {
        return electionCandidateRepository.isCandidateRegistered(electionId, candidateId, localityId);
    }

    public List<?> getRegisteredCandidates(HttpServletRequest request, String election, String candidateTypeId) {
        Long candidateType = convertToLong(candidateTypeId);
        Long electionId = convertToLong(election);
        User user = userService.getUser(request);

        Integer userLocality = user.getLocalityId();
        String userCounty = user.getCounty();

       if(candidateType == 1 || candidateType == 3){
           return getRegisteredCandidatesForLocalElections(electionId, userLocality, candidateType);
       }
       if(candidateType == 2 || candidateType == 8){
            return getRegisteredCandidatesForCountyElections(electionId, userLocality, userCounty);
       }
       else return getRegisteredCandidatesForCountry(electionId, candidateType);
    }

    private List<?> getRegisteredCandidatesForLocalElections(Long electionId, Integer localityId, Long candidateTypeId) {
        //id 1 and id 3 //Primar sau consiliu local
        List<List<Long>> list = electionCandidateRepository.findByElectionIdAndCompetingInLocality(electionId, localityId);

        List<RegisteredCandidatesResponse> registeredCandidates = new ArrayList<>();
        list.forEach(item -> {
            Candidate candidate = candidateService.getCandidateById(Math.toIntExact(item.get(4)));
            if(candidate.getCandidateTypeId() == candidateTypeId){
                RegisteredCandidatesResponse registeredCandidatesResponse = new RegisteredCandidatesResponse(candidate);
                registeredCandidates.add(registeredCandidatesResponse);
            }
        });

        return registeredCandidates;
    }

    private List<?> getRegisteredCandidatesForCountyElections(Long electionId, Integer localityId, String county) {
        //id 2 and id 8 //Consiliu judetean sau Presedinte consiliu judetean
        List<?> registeredCandidates = electionCandidateRepository.findByElectionIdAndCompetingInLocalityAndCounty(electionId, localityId, county);
        return registeredCandidates;
    }

    private List<?> getRegisteredCandidatesForCountry(Long electionId, Long candidateTypeId) {
        //id 4, 5, 6, 7
        List<?> registeredCandidates = electionCandidateRepository.findByElectionIdAndCandidateTypeId(electionId, candidateTypeId);

        return registeredCandidates;
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
