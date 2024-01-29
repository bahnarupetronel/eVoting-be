package com.example.demo.service;

import com.example.demo.model.Gender;
import com.example.demo.repository.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenderService {
    private final GenderRepository genderRepository;

    public List<Gender> getGenders(){
        return genderRepository.findAll();
    }
}
