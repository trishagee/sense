package com.mechanitis.demo.sense.mood;

import java.util.Optional;
import java.util.Set;

public class MoodyMessage {
    private final Set<Mood> moods;

    private MoodyMessage(Set<Mood> moods) {
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

    public static class Factory {
        public static Optional<MoodyMessage> createMessageFromMoods(Set<Mood> moods) {
            return moods.size() == 0 ? Optional.empty() : Optional.of(new MoodyMessage(moods));
        }
    }
}
