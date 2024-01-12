package com.example.demo.controller;

import com.example.demo.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController@RequiredArgsConstructor
@RequestMapping("/education")
public class EducationController {
    private final EducationService educationService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUsers(){
        educationService.createEducationsForCompetitors();
    }

}
