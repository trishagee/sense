package com.mechanitis.demo.sense.mood;

import java.util.Set;

public class MoodyMessage {
    private final Set<Mood> moods;

    public MoodyMessage(Set<Mood> moods) {
        this.moods = moods;
    }

    public boolean hasMood(Mood mood) {
        return moods.contains(mood);
    }

    @Override
    public String toString() {
        return "MoodyMessage{" +
               "moods=" + moods +
               '}';
    }
}
