package com.example.demo.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.amazonaws.services.qldb.model.ResourceAlreadyExistsException;
import com.example.demo.dto.ElectionDTO;
import com.example.demo.model.Election;
import com.example.demo.model.ElectionType;
import com.example.demo.repository.ElectionRepository;
import com.example.demo.repository.ElectionTypesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.demo.utils.Convert.convertToLong;

@Service
public class ElectionService {
    @Autowired
    private ModelMapper modelMapper;
    private final ElectionRepository electionRepository;
    private final ElectionTypesRepository electionTypesRepository;

    public ElectionService(ElectionRepository electionRepository, ElectionTypesRepository electionTypesRepository){
        this.electionRepository = electionRepository;
        this.electionTypesRepository = electionTypesRepository;
    }
    public Long addElection(@RequestBody ElectionDTO electionDTO){
        Election election = modelMapper.map(electionDTO, Election.class);
        if(electionRepository.existsBy(election.getTitle(), Math.toIntExact(election.getTypeId()), election.getStartDate(), election.getEndDate())){
            throw new ResourceAlreadyExistsException("A similar event already exists. " +
                    "Please try again !");
        }
        Election createdElection = electionRepository.save(election);
        return createdElection.getElectionId();
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

    public Election getElectionById(String id) {
        Long electionId = convertToLong(id);
        return getElectionById(electionId);
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

    public List<ElectionType> getTypes() {
        return electionTypesRepository.findAll();
    }

    private LocalDateTime getLocalDateTime() {
        LocalDateTime today = LocalDateTime.now();
        return today;
    }

}
