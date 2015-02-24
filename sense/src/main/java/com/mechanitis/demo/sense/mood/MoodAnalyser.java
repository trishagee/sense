package com.mechanitis.demo.sense.mood;

import java.util.List;

import static com.mechanitis.demo.sense.mood.Mood.HAPPY;
import static com.mechanitis.demo.sense.mood.Mood.SAD;
import static java.util.Arrays.asList;

public class MoodAnalyser {
    private static final List<MoodIndicator> MOOD_INDICATORS = asList(new MoodIndicator("happy", HAPPY),
                                                                      new MoodIndicator("good", HAPPY),
                                                                      new MoodIndicator("great", HAPPY),
                                                                      new MoodIndicator("keen", HAPPY),
                                                                      new MoodIndicator("awesome", HAPPY),
                                                                      new MoodIndicator("marvelous", HAPPY),
                                                                      new MoodIndicator("yay", HAPPY),
                                                                      new MoodIndicator("pleased", HAPPY),
                                                                      new MoodIndicator("sad", SAD),
                                                                      new MoodIndicator("mad", SAD),
                                                                      new MoodIndicator("blargh", SAD),
                                                                      new MoodIndicator("boo", SAD),
                                                                      new MoodIndicator("terrible", SAD),
                                                                      new MoodIndicator("horrible", SAD),
                                                                      new MoodIndicator("bad", SAD),
                                                                      new MoodIndicator("awful", SAD));

    private MoodAnalyser() {
    }

    public static MoodyMessage analyseMood(String fullMessage) {
        // TODO: extract message body

        // TODO: find the mood

        // TODO: return a MoodyMessage
        return null;
    }

    private static class MoodIndicator {
        private final String word;
        private final Mood mood;

        public MoodIndicator(String word, Mood mood) {
            this.word = word;
            this.mood = mood;
        }
    }

}
