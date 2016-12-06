package com.mechanitis.demo.sense.mood;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.mechanitis.demo.sense.mood.Mood.HAPPY;
import static com.mechanitis.demo.sense.mood.Mood.SAD;
import static com.mechanitis.demo.sense.twitter.TweetParser.getTweetMessageFrom;
import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static java.util.stream.Collectors.joining;

public class MoodAnalyser {
    private static final Map<String, Mood> WORD_TO_MOOD =
            ofEntries(entry("happy", HAPPY),
                      entry("good", HAPPY),
                      entry("great", HAPPY),
                      entry("keen", HAPPY),
                      entry("awesome", HAPPY),
                      entry("marvelous", HAPPY),
                      entry("yay", HAPPY),
                      entry("pleased", HAPPY),
                      entry("sad", SAD),
                      entry("mad", SAD),
                      entry("blargh", SAD),
                      entry("boo", SAD),
                      entry("terrible", SAD),
                      entry("horrible", SAD),
                      entry("bad", SAD),
                      entry("awful", SAD));

    public static String analyseMood(String fullMessage) {
        String[] wordsInMessage = getTweetMessageFrom(fullMessage).split("\\s");
        return Arrays.stream(wordsInMessage)
                     .map(String::toLowerCase)
                     .distinct()
                     .map(WORD_TO_MOOD::get)
                     .filter(Objects::nonNull)
                     .distinct()
                     .map(Enum::name)
                     .collect(joining(","));
    }

    private MoodAnalyser() {
    }
}
