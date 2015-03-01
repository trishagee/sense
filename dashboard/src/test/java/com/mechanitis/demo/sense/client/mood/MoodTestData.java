package com.mechanitis.demo.sense.client.mood;

import com.mechanitis.demo.sense.client.StubService;

import java.util.Random;

public class MoodTestData {
    private static final String[] POSSIBLE_MOODS = new String[]{"HAPPY", "SAD", "HAPPY,SAD"};

    public static void main(String[] args) {
        Random random = new Random();
        new StubService("/moods/", 8082, () -> POSSIBLE_MOODS[random.nextInt(3)]).run();
    }
}
