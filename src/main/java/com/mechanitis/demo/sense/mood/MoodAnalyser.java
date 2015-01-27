package com.mechanitis.demo.sense.mood;

import java.util.List;
import java.util.Optional;

import static com.mechanitis.demo.sense.mood.MoodyMessage.Mood.Happy;
import static com.mechanitis.demo.sense.mood.MoodyMessage.Mood.Sad;
import static java.util.Arrays.asList;

public class MoodAnalyser {
    private static final List<String> YAY = asList("happy", "good", "great", "keen", "awesome", "marvelous", "yay", "pleased");
    private static final List<String> BOO = asList("sad", "mad", "blargh", "boo", "terrible", "horrible", "bad", "awful");


    public MoodyMessage analyse(String message) {
        MoodyMessage moodyMessage = new MoodyMessage();
        if (stringContainsSentiment(message, YAY)) {
            moodyMessage.addMood(Happy);
        }
        if (stringContainsSentiment(message, BOO)) {
            moodyMessage.addMood(Sad);
        }
        return moodyMessage;
    }

    static boolean stringContainsSentiment(final String sentence, final List<String> items) {
        return items.stream().anyMatch(sentence::contains);
    }

}
