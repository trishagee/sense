package com.mechanitis.demo.sense.twitter.connector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TwitterConnectorTest {
    private List<String> receivedTweets = new ArrayList<>();
    //        @Subject
    private TwitterConnection connection = new TwitterConnection(receivedTweets::add);

    @Test
    @DisplayName("should alert a listener of tweets in order")
    void testTweetsComeInOrder() {
        connection.processTweets(Stream.of("first", "second"));

        assertAll(
                () -> {
                    assertEquals("first", receivedTweets.get(0));
                    assertEquals("second", receivedTweets.get(1));
                }
        );
    }

    @Test
    @DisplayName("should not pass on deleted tweets")
    void shouldIgnoreDeletedTweets() {
//        given:
        String deletedTweet = "{\"delete\":{\"status\":{\"id\":545654287926190080,\"user_id\":954217777}," +
                              "\"timestamp_ms\":\"1421930220668\"}}\n";
        String createdTweet = "{\"created_at\":\"Thu Jan 22 12:37:00 +0000 2015\",\"id\":558241922696089600," +
                              "\"text\":\"Bom diaa\", \"user\":{\"id\":2860570708}\n";

//        when:
        connection.processTweets(Stream.of(deletedTweet, createdTweet));

        assertAll(
                () -> {
                    assertEquals(createdTweet, receivedTweets.get(0));
                    assertEquals(1, receivedTweets.size());
                }
        );
    }
}
