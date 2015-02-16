package com.mechanitis.demo.sense.client.mood;

@FunctionalInterface
public interface MoodListener {
    void onMessage(TweetMood message);
}
