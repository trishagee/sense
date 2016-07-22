package com.mechanitis.demo.sense.mood;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoodAnalyserTest {
    private static final String TWITTER_MESSAGE_TEMPLATE =
            "tweet = {\"created_at\":\"Tue Jan 27 12:37:11 +0000 2015\"," +
            "\"id\":560053908144275456,\"id_str\":\"560053908144275456\"," +
            "\"text\":\"%s\",\"source\":\"twitter\"}";

    @Nested
    @DisplayName("when a message contains a happy sentiment")
    class HappyCases {
        @Test
        @DisplayName("should identify messages with a single happy sentiment that is all lower case")
        void checkHappyMessages() {
            //when
            String moodyMessage = MoodAnalyser.analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I am so happy today"));

            //then
            assertEquals("HAPPY", moodyMessage);
        }

        @Test
        @DisplayName("should identify messages with a single happy sentiment that is not lower case")
        void checkMixedCaseMessages() {
            //when:
            String moodyMessage = MoodAnalyser.analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I am so Awesome today"));

            //then:
            assertEquals("HAPPY", moodyMessage);
        }
    }

    @Nested
    @DisplayName("when a message contains a sad sentiment")
    class SadCases {
        private String moodyMessage = MoodAnalyser.analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I am so sad today"));

        @Test
        @DisplayName("should identify a single lower case sentiment")
        void checkSadMessage() {
            assertEquals("SAD", moodyMessage);
        }
    }

    @Nested
    @DisplayName("when a message contains happy and sad sentiments")
    class MixedCases {
    @Test
    @DisplayName("should return both happy and sad for a single instance of each sentiment")
    void checkMixedMessages() {
        String moodyMessage = MoodAnalyser
                .analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I am so sad today it almost makes me happy"));

        assertEquals("SAD,HAPPY", moodyMessage);
    }

        @Test
        @DisplayName("should return only one mood of each type when multiple words match the same sentiment")
        void checkMultipleMoods() {
            String moodyMessage = MoodAnalyser
                    .analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "Yesterday I was sad sad sad, but today is awesome"));

            assertEquals("SAD,HAPPY", moodyMessage);
        }

        @Test
        @DisplayName("should return only one mood of each type when multiple words of different cases match the same " +
                     "sentiment")
        void checkMultipleMoodsMixedCase() {
            String moodyMessage = MoodAnalyser
                    .analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "Yesterday I was sad sad SAD, but today is awesome"));

            assertEquals("SAD,HAPPY", moodyMessage);
        }

    }

    @Nested
    @DisplayName("when a message contains a sad sentiment")
    class NoMoodCases {
        private String moodyMessage = MoodAnalyser.analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I don't care"));

        @Test
        @DisplayName("should not have any mood for messages that are neither happy or sad")
        void checkMessagesWithoutMood() {
            assertEquals("", moodyMessage);
        }
    }

}
