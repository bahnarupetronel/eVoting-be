package com.example.demo.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.amazonaws.services.glue.model.EntityNotFoundException;
import com.amazonaws.services.qldb.model.ResourceAlreadyExistsException;
import com.example.demo.dto.ElectionDTO;
import com.example.demo.enums.EnumRole;
import com.example.demo.model.*;
import com.example.demo.payload.ElectionAndTypesResponse;
import com.example.demo.payload.ElectionPublish;
import com.example.demo.repository.ElectionRepository;
import com.example.demo.repository.ElectionTypesRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.example.demo.utils.Convert.convertToLong;

@Service@RequiredArgsConstructor
public class ElectionService {
    @Autowired
    private ModelMapper modelMapper;
    private final ElectionRepository electionRepository;
   private final ElectionTypeService electionTypeService;
    private final CandidateTypeService candidateTypeService;
    private final UserService userService;

    public void addElection(ElectionDTO electionDTO){
        Election election = modelMapper.map(electionDTO, Election.class);
        if(electionRepository.existsBy( Math.toIntExact(election.getTypeId()), election.getStartDate(), election.getEndDate())){
            throw new ResourceAlreadyExistsException("A similar event already exists. " +
                    "Please try again !");
        }
        electionRepository.save(election);
    }

    public void publishEvent(ElectionPublish electionPublish){
        Election election = electionRepository.findById(electionPublish.getElectionId()).orElse(null);
        assert election != null;
        election.setPublished(true);
        electionRepository.save(election);
    }

    public List<Election> getPublishedElections() {
        return electionRepository.findByPublishedTrue();
    }

    public List<Election> getUnpublishedElections() {
        return electionRepository.findByPublishedFalse();
    }

    public List<Election> getLiveElections() {
        return electionRepository.findLiveElections(getLocalDateTime());
    }

    public List<Election> getUpcomingElections() {
        return electionRepository.findUpcomingElections(getLocalDateTime());
    }

    public List<Election> getFinishedElections() {
        return electionRepository.findFinishedElections(getLocalDateTime());
    }

    public Election getElectionById(String id, HttpServletRequest request) {
        Long electionId = convertToLong(id);
       Election election = getElectionById(electionId);
       if(!election.isPublished() ) {
           User user = userService.getUser(request);
           Set<Role> roles =  user.getRoles();
           boolean isAdmin = roles.stream()
                   .anyMatch(role -> role.getName() == EnumRole.admin);
           if(!isAdmin)
           throw new EntityNotFoundException(("Resursa nu exista."));
       }
       return election;
    }

    public Election getElectionById(Long id) {
        return electionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(new String("Evenimentul cu id-ul " + id + " nu exista!")));
    }

    public void deleteElection (Long electionId){
        Election election = getElectionById(electionId);
        if(!election.isPublished())
            electionRepository.deleteById(electionId);
        else  throw new IllegalStateException(new String("You can't remove this event! It is published."));
    }

    public ElectionAndTypesResponse getElectionAndTypes (String id){
        Long electionId = convertToLong(id);
        Election election = getElectionById(electionId);
        ElectionAndTypesResponse electionAndTypesResponse = new ElectionAndTypesResponse();
        electionAndTypesResponse.setElection(election);
        List < CandidateType> candidateTypes = candidateTypeService.getTypesByElectionType(String.valueOf(election.getTypeId()));
        electionAndTypesResponse.setCandidateTypes(candidateTypes);
        return electionAndTypesResponse;
    }

    public List<ElectionType> getTypes() {
        return electionTypeService.getTypes();
    }

    private LocalDateTime getLocalDateTime() {
        LocalDateTime today = LocalDateTime.now();
        return today;
    }

}
