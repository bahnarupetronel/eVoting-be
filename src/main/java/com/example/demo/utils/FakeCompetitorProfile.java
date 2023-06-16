package com.example.demo.utils;

import com.example.demo.repository.EducationRepository;
import com.example.demo.service.EducationService;
import com.github.javafaker.Faker;

public class FakeCompetitorProfile {

    private static final int NUMBER_OF_FAKE_RECORDS = 10;
    private final Faker faker;

    public FakeCompetitorProfile(Faker faker) {

        this.faker = faker;
    }

    public void generateFakeData() {
        for (int i = 0; i < NUMBER_OF_FAKE_RECORDS; i++) {

        }
    }

}