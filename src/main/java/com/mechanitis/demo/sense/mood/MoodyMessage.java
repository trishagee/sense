package com.mechanitis.demo.sense.mood;

import java.util.HashSet;
import java.util.Set;

public class MoodyMessage {
    private final Set<Mood> moods = new HashSet<>();

    public void addMood(Mood mood) {
        moods.add(mood);
    }

    public boolean hasMood(Mood mood) {
        return moods.contains(mood);
    }

    enum Mood {
        Sad, Happy
    }
}
