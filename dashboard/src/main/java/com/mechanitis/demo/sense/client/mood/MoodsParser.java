package com.mechanitis.demo.sense.client.mood;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface MoodsParser {
    static TweetMood parse(String moodsAsCsv) {
        Set<Mood> moodSet = Stream.of(moodsAsCsv.split(","))
                                  .filter(s -> !s.isEmpty())
                                  .map(Mood::valueOf)
                                  .collect(Collectors.toSet());
        return new TweetMood(moodSet);
    }
}
