package com.mechanitis.demo.sense.mood;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoodAnalyserTest {
    private static final String TWITTER_MESSAGE_TEMPLATE =
            "tweet = {\"created_at\":\"Tue Jan 27 12:37:11 +0000 2015\"," +
            "\"id\":560053908144275456,\"id_str\":\"560053908144275456\"," +
            "\"text\":\"%s\",\"source\":\"twitter\"}";

    @Test
    @DisplayName("should correctly identify happy messages")
    void checkHappyMessages() {
        //when
        String moodyMessage = MoodAnalyser.analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I am so happy today"));

        //then
        assertEquals("HAPPY", moodyMessage);
    }

    @Test
    @DisplayName("should correctly identify happy messages that are not lower case")
    void checkMixedCaseMessages() {
        //when:
        String moodyMessage = MoodAnalyser.analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I am so Awesome today"));

        //then:
        assertEquals("HAPPY", moodyMessage);
    }

    @Test
    @DisplayName("should correctly identify sad messages")
    void checkSadMessages() {
//        when:
        String moodyMessage = MoodAnalyser.analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I am so sad today"));

//        then:
        assertEquals("SAD", moodyMessage);
    }

    @Test
    @DisplayName("should correctly identify mixed messages")
    void checkMixedMessages() {
//        when:
        String moodyMessage = MoodAnalyser
                .analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I am so sad today it almost makes me happy"));

//        then:
        assertEquals("SAD,HAPPY", moodyMessage);
    }

    @Test
    @DisplayName("should correctly identify mixed messages with multiple moods")
    void checkMultipleMoods() {
//        when:
        String moodyMessage = MoodAnalyser
                .analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "Yesterday I was sad sad sad, but today is awesome"));

//        then:
        assertEquals("SAD,HAPPY", moodyMessage);
    }

    @Test
    @DisplayName("should not have any mood for messages that are neither happy or sad")
    void checkMessagesWithoutMood() {
//        when:
        String moodyMessage = MoodAnalyser.analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I don't care"));

//        then:
        assertEquals("", moodyMessage);
    }

}
