package com.example.demo.service;

import com.example.demo.model.ReferendumOptions;
import com.example.demo.repository.ReferendumOptionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReferendumOptionsService {
    private final ReferendumOptionsRepository referendumOptionsRepository;

    public List<ReferendumOptions> getOptions (){
        return referendumOptionsRepository.findAll();
    }
}
