package com.mechanitis.demo.sense.mood;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static MoodyMessage analyseMood(String message) {
        Set<Mood> messageMoods = MOOD_INDICATORS.stream()
                                                .filter(moodIndicator -> message.contains(moodIndicator.indicator))
                                                .map((MoodIndicator moodIndicator) -> moodIndicator.mood)
                                                .collect(Collectors.toSet());
        return new MoodyMessage(messageMoods);
    }

    private static class MoodIndicator {
        private final String indicator;
        private final Mood mood;

        public MoodIndicator(String indicator, Mood mood) {
            this.indicator = indicator;
            this.mood = mood;
        }
    }

}
