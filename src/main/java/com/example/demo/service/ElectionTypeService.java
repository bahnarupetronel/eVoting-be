package com.example.demo.service;

import com.example.demo.model.ElectionType;
import com.example.demo.repository.ElectionTypesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElectionTypeService {
    private final ElectionTypesRepository electionTypesRepository;

    public List<ElectionType> getTypes() {
        return electionTypesRepository.findAll();
    }
}
