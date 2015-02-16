package com.mechanitis.demo.sense.client.mood;

import java.util.HashSet;
import java.util.Set;

import static com.mechanitis.demo.sense.client.mood.Mood.HAPPY;
import static com.mechanitis.demo.sense.client.mood.Mood.SAD;

public class TweetMood {
    private final Set<Mood> moods = new HashSet<>();

    public void add(Mood mood) {
        moods.add(mood);
    }

    public boolean isHappy() {
        return moods.contains(HAPPY);
    }

    public boolean isSad() {
        return moods.contains(SAD);
    }

    public boolean isConfused() {
        return isHappy() && isSad();
    }

    public Set<Mood> getMoods() {
        return moods;
    }

}
