package com.example.demo.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.example.demo.dto.ElectionDTO;
import com.example.demo.model.Election;
import com.example.demo.model.ElectionType;
import com.example.demo.repository.ElectionRepository;
import com.example.demo.repository.ElectionTypesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public Long addElection(ElectionDTO electionDTO){
        Election election = modelMapper.map(electionDTO, Election.class);
        Election createdElection = electionRepository.save(election);
        return createdElection.getElectionId();
    }

    public List<Election> getElections() {
        return electionRepository.findByPublishedTrue();
    }

    public List<Election> getLiveElections() {
        Date currentDate = new Date();
        return electionRepository.findLiveElections(currentDate);
    }

    public List<Election> getUpcomingElections() {
        Date currentDate = new Date();
        return electionRepository.findUpcomingElections(currentDate);
    }

    public List<Election> getFinishedElections() {
        Date currentDate = new Date();
        return electionRepository.findFinishedElections(currentDate);
    }

    public Election getElectionById(Long id) {
        return electionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(new String("Evenimentul cu id-ul " + id + " nu exista!")));
    }

    public List<ElectionType> getTypes() {
        return electionTypesRepository.findAll();
    }
}
