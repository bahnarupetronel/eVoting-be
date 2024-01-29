package com.example.demo.controller;

import com.example.demo.model.Gender;
import com.example.demo.service.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gender")
@RequiredArgsConstructor
public class GenderController {
    private final GenderService genderService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Gender> getGenders(){
        return genderService.getGenders();
    }
}
