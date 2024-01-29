package com.example.demo.service;

import com.example.demo.model.CandidateType;
import com.example.demo.repository.CandidateTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.utils.Convert.convertToLong;

@Service@RequiredArgsConstructor
public class CandidateTypeService {
    private final CandidateTypeRepository candidateTypeRepository;

    public List<CandidateType> getTypes(){
        return candidateTypeRepository.findAll();
    }

    public List<CandidateType> getTypesByElectionType(String id){
        Long electionTypeId = convertToLong(id);
        return candidateTypeRepository.findByElectionTypeId(Math.toIntExact(electionTypeId));
    }
}
