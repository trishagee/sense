package com.mechanitis.demo.sense.mood;

import java.util.Map;

import static com.mechanitis.demo.sense.mood.Mood.HAPPY;
import static com.mechanitis.demo.sense.mood.Mood.SAD;
import static com.mechanitis.demo.sense.twitter.TweetParser.getTweetMessageFrom;

public class MoodAnalyser {
    private static final Map<String, Mood> WORD_TO_MOOD
            = Map.ofEntries(
            Map.entry("happy", HAPPY),
            Map.entry("good", HAPPY),
            Map.entry("great", HAPPY),
            Map.entry("keen", HAPPY),
            Map.entry("awesome", HAPPY),
            Map.entry("marvelous", HAPPY),
            Map.entry("yay", HAPPY),
            Map.entry("pleased", HAPPY),
            Map.entry("sad", SAD),
            Map.entry("mad", SAD),
            Map.entry("blargh", SAD),
            Map.entry("boo", SAD),
            Map.entry("terrible", SAD),
            Map.entry("horrible", SAD),
            Map.entry("bad", SAD),
            Map.entry("awful", SAD));

    private MoodAnalyser() {
    }

    public static String analyseMood(String fullMessage) {
        String[] wordsInMessage = getTweetMessageFrom(fullMessage).split("\\s");
        //TODO: figure out the unique moods in this message and return as CSV
        return null;
    }
}
