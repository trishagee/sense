package com.mechanitis.demo.sense.mood;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class MoodAnalyser {
    private static final List<String> YAY = asList("happy", "good", "great", "keen", "awesome", "marvelous", "yay", "pleased");
    private static final List<String> BOO = asList("sad", "mad", "blargh", "boo", "terrible", "horrible", "bad", "awful");


    public Optional<MoodyMessage> analyse(String message) {
        if (stringContainsSentiment(message, YAY)) {
            return Optional.of(new HappyMessage());
        } else if (stringContainsSentiment(message, BOO)) {
            return Optional.of(new SadMessage());
        }
        return Optional.empty();
    }

    static boolean stringContainsSentiment(final String sentence, final List<String> items) {
        return items.stream().anyMatch(sentence::contains);
    }

}
