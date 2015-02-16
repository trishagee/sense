package com.mechanitis.demo.sense.client.mood;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static com.mechanitis.demo.sense.client.mood.Mood.HAPPY;
import static com.mechanitis.demo.sense.client.mood.Mood.SAD;

public class TweetMood {
    private final Set<Mood> moods = new HashSet<>();

    public void add(Mood mood) {
        moods.add(mood);
    }

    public boolean isHappy() {
        return moods.contains(HAPPY);
    }

    public boolean isSad() {
        return moods.contains(SAD);
    }

    public void ifSad(Consumer<? super TweetMood> consumer) {
        if (isSad()) {
            consumer.accept(this);
        }
    }

    public boolean isConfused() {
        return isHappy() && isSad();
    }

    public Set<Mood> getMoods() {
        return moods;
    }

    public void ifHappy(Consumer<TweetMood> tweetMoodConsumer) {

    }

    public void ifConfused(Consumer<TweetMood> tweetMoodConsumer) {


    }
}
