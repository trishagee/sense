package com.mechanitis.demo.sense.client.mood;

import com.mechanitis.demo.sense.client.RandomGenerator;
import com.mechanitis.demo.sense.client.StubService;

public class MoodTestData {
    private static final String[] POSSIBLE_MOODS = {"HAPPY", "SAD", "HAPPY,SAD"};

    public static void main(String[] args) {
        new StubService("/moods/", 8082, RandomGenerator.of(POSSIBLE_MOODS)).run();
    }
}