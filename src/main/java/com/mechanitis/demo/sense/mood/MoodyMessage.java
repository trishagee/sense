package com.mechanitis.demo.sense.mood;

import com.mechanitis.demo.sense.message.Message;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.asList;

public class MoodyMessage implements Message {
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
        static MoodyMessage createMessageFromMood(Mood mood) {
            return new MoodyMessage(new HashSet<>(asList(mood)));
        }
    }
}
