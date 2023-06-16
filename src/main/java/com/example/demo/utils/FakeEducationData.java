package com.example.demo.utils;

import com.example.demo.repository.EducationRepository;
import com.example.demo.service.EducationService;
import com.github.javafaker.Faker;

public class FakeEducationData {

    private static final int NUMBER_OF_FAKE_RECORDS = 10;
    private final EducationRepository educationRepository;
    private final Faker faker;

    public FakeEducationData(EducationRepository educationRepository, Faker faker) {
        this.educationRepository = educationRepository;
        this.faker = faker;
    }


}